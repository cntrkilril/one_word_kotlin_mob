package com.example.oneword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.oneword.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: FragmentType = FragmentType.LOGIN
    private val model = MainActivityViewModel()
    private val api: MyApi = MyApiImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        model.currentUser.observe(this) { newUser ->
            if (newUser == null) {
                goToLoginFragment()
            } else {
                showMessage(getString(R.string.empty_text))
                goToMainFragment()
            }
        }
    }

    fun showMessage(text: String) {
        binding.message.text = text
    }

    fun login(login: String, password: String) {
        if (login == "" || password == "") {
            showMessage(getString(R.string.non_empty_fields_message))
            return
        }
        val loginCheck = model.login(login, password)
        if (!loginCheck) {
            showMessage(getString(R.string.bad_login_or_password_message))
        }
    }

    fun logout() {
        model.logout()
    }

    fun register(login: String, password: String) {
        if (login == "" || password == "") {
            showMessage(getString(R.string.non_empty_fields_message))
            return
        }
        if (model.register(login, password)) {
            showMessage(getString(R.string.succes_register_message))
        } else {
            showMessage(getString(R.string.login_already_exist_message))
        }
    }

    fun addToStory(resultGame: ResultGame) {
        model.addToStory(resultGame)
    }

    fun getCurrentUser(): User? {
        return model.currentUser.value
    }

    fun getCurrentLevel(): String? {
        return model.currentLevel.value
    }

    fun getResWord(): String? {
        return model.resWord.value
    }

    override fun onBackPressed() {
        if (currentFragment == FragmentType.GAME) {
            val snack = Snackbar.make(
                this.binding.root,
                getString(R.string.warning_backroll_message),
                Snackbar.LENGTH_LONG
            )
            snack.show()
            currentFragment = FragmentType.GAME2
        } else {
            super.onBackPressed()
        }
        showMessage("")
    }

    fun goToMainFragment() {
        supportFragmentManager.commit {
            replace(binding.frameFragments.id, MainFragment.newInstance())
        }
        currentFragment = FragmentType.MAIN
    }

    fun goToLoginFragment() {
        supportFragmentManager.commit {
            replace(binding.frameFragments.id, LoginFragment.newInstance())
        }
        currentFragment = FragmentType.LOGIN
    }

    fun goToGameFragment(indexOfLevel: Int) {

        model.startGame(indexOfLevel)

        supportFragmentManager.commit {
            replace(binding.frameFragments.id, GameFragment.newInstance())
            addToBackStack("main")
        }

        currentFragment = FragmentType.GAME
    }

    fun goToAboutFragment() {
        supportFragmentManager.commit {
            replace(binding.frameFragments.id, RulesFragment.newInstance())
            addToBackStack("main")
        }
        currentFragment = FragmentType.ABOUT
    }

    fun goToStoryFragment() {
        supportFragmentManager.commit {
            replace(binding.frameFragments.id, StoryFragment.newInstance())
            addToBackStack("main")
        }
        currentFragment = FragmentType.STORY
    }

}
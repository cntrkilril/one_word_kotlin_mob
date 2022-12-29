package com.example.oneword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.oneword.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: FragmentType = FragmentType.LOGIN
    private val api: MyApi = MyApiImpl()

    private var currentUser: User? = null
    var currentLevel: String = ""
    var resWord: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (currentUser == null) {
            goToLoginFragment()
        } else {
            goToMainFragment()
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
        val user: User? = api.login(login, password)
        if (user == null) {
            showMessage(getString(R.string.bad_login_or_password_message))
        } else {
            currentUser = user
            goToMainFragment()
            showMessage(getString(R.string.empty_text))
        }
    }

    fun logout() {
        val user = currentUser
        if (user !== null) {
            if (api.logout(user.login)) {
                goToLoginFragment()
                showMessage(getString(R.string.empty_text))
                currentUser = null
            } else {
                showMessage(getString(R.string.login_error_message))
            }
        }
    }

    fun register(login: String, password: String) {
        if (login == "" || password == "") {
            showMessage(getString(R.string.non_empty_fields_message))
            return
        }
        if (api.register(login, password)) {
            showMessage(getString(R.string.succes_register_message))
        } else {
            showMessage(getString(R.string.login_already_exist_message))
        }
    }

    fun addToStory(resultGame: ResultGame) {
        val user = currentUser
        if (user !== null) {
            api.addToStory(user, resultGame)
        }
    }

    fun getStoryByCurrentUser(): ArrayList<ResultGame> {
        val user = currentUser
        if (user !== null) {
            return api.getStoryByUser(user)
        }
        return arrayListOf()
    }

    fun processWord(wordInputText: List<String>): ArrayList<LetterClass> {
        return api.processWord(wordInputText, resWord)
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

        resWord = api.getResWords(indexOfLevel)
        currentLevel = api.getLevel(indexOfLevel)

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
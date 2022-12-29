package com.example.oneword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.oneword.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: String = "login"

    private val users: ArrayList<User> = arrayListOf(User("admin", "admin"))
    private val levels: ArrayList<Int> = arrayListOf(4, 5, 6)
    private val words: ArrayList<ArrayList<String>> = Words().list

    var currentUser: User? = null
    var currentLevel: Int = 0
    var storyList: ArrayList<Story> = arrayListOf()
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

    fun login(login: String, password: String) {
        if (login == "" || password == "") {
            showMessage("Нужно заполнить оба поля")
            return
        }
        for (user in users) {
            if (user.login == login && user.password == password) {
                currentUser = User(password, login)
                goToMainFragment()
                showMessage("")
                return
            }
        }
        showMessage("Неправильный логин или пароль")
    }

    fun showMessage(text: String) {
        binding.message.text = text
    }

    fun logout() {
        goToLoginFragment()
        currentUser = null
    }

    fun register(login: String, password: String) {
        if (login == "" || password == "") {
            showMessage("Нужно заполнить оба поля")
            return
        }
        for (user in users) {
            if (user.login == login) {
                showMessage("Пользователь с таким логином уже существует")
                return
            }
        }
        users.add(User(password, login))
        showMessage("Вы зарегистрированы! Войдите в приложение")
    }

    fun addToStory(resultGame: ResultGame) {
        for (story in storyList) {
            if (story.user.login == currentUser?.login) {
                story.resultsGames.add(resultGame)
                return
            }
        }
        if (currentUser !== null) {
            storyList.add(Story(currentUser!!, arrayListOf(resultGame)))
        }
    }

    fun getStoryByCurrentUser(): ArrayList<ResultGame> {
        if (currentUser !== null) {
            for (story in storyList) {
                if (story.user.login == currentUser!!.login) {
                    return story.resultsGames
                }
            }
        }
        return arrayListOf()
    }

    override fun onBackPressed() {
        if (currentFragment == "game") {
            val snack = Snackbar.make(
                this.binding.root,
                "Если вы выйдите, игра не будет учитана в общем прогрессе. Нажмите еще раз для выхода",
                Snackbar.LENGTH_LONG
            )
            snack.show()
            currentFragment = "game2"
        } else {
            super.onBackPressed()
        }
        showMessage("")
    }

    fun goToMainFragment() {
        supportFragmentManager.commit {
            replace(binding.frameFragments.id, MainFragment.newInstance())
        }
        currentFragment = "main"
    }

    fun goToLoginFragment() {
        supportFragmentManager.commit {
            replace(binding.frameFragments.id, LoginFragment.newInstance())
        }
        currentFragment = "login"
    }

    fun goToGameFragment(indexOfLevel: Int) {

        resWord = Utils().randomWord(words[indexOfLevel])
        currentLevel = levels[indexOfLevel]

        supportFragmentManager.commit {
            replace(binding.frameFragments.id, GameFragment.newInstance())
            addToBackStack("main")
        }

        currentFragment = "game"
    }

    fun goToAboutFragment() {
        supportFragmentManager.commit {
            replace(binding.frameFragments.id, RulesFragment.newInstance())
            addToBackStack("main")
        }
        currentFragment = "about"
    }

    fun goToStoryFragment() {
        supportFragmentManager.commit {
            replace(binding.frameFragments.id, StoryFragment.newInstance())
            addToBackStack("main")
        }
        currentFragment = "story"
    }

}
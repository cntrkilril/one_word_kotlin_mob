package com.example.oneword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.logging.Level

class MainActivityViewModel() : ViewModel() {
    private val api = MyApiImpl()
    val currentUser: MutableLiveData<User?> = MutableLiveData(null)
    val currentLevel: MutableLiveData<String> = MutableLiveData("")
    val resWord: MutableLiveData<String> = MutableLiveData("")

    fun login(login: String, password: String): Boolean {
        currentUser.value = api.login(login, password)
        return currentUser.value != null
    }

    fun logout(): Boolean {
        val user = currentUser.value
        if (user != null) {
            return if (api.logout(user.login)) {
                currentUser.value = null
                true
            } else {
                false
            }
        }
        return false
    }

    fun register(login: String, password: String): Boolean {
        return api.register(login, password)
    }

    fun addToStory(resultGame: ResultGame) {
        val user = currentUser.value
        if (user !== null) {
            api.addToStory(user, resultGame)
        }
    }

    fun startGame(indexOfLevel: Int) {
        resWord.value = api.getResWords(indexOfLevel)
        currentLevel.value = api.getLevel(indexOfLevel)
    }

}
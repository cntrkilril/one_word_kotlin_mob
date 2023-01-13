package com.example.oneword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoryViewModel(private val user: User?) : ViewModel() {
    private val api = MyApiImpl()
    val story: MutableLiveData<ArrayList<ResultGame>> by lazy {
        MutableLiveData<ArrayList<ResultGame>>()
    }

    init {
        getStoryByCurrentUser()
    }

    fun getStoryByCurrentUser() {
        if (user !== null) {
            story.value = api.getStoryByUser(user)
        } else {
            story.value = arrayListOf()
        }
    }
}
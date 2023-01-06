package com.example.oneword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.nio.file.attribute.AclEntryFlag
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

data class CheckWordReturnType(
    val word: ArrayList<LetterClass>,
    val status: StatusCheckWord,
    val time: Double
)

data class FinishGameResult(
    val time: Double,
    val winFlag: Boolean,
    val resWord: String
)

class GameViewModel(initResWord: String, initLevel: String) : ViewModel() {
    private val api = MyApiImpl()
    val winFlag: MutableLiveData<Boolean> = MutableLiveData(false)
    val level: MutableLiveData<String> = MutableLiveData(initLevel)
    val startTime: MutableLiveData<Double> = MutableLiveData(Date().time.toDouble())
    val resTime: MutableLiveData<Double> = MutableLiveData(Date().time.toDouble())
    val resWord: MutableLiveData<String> = MutableLiveData(initResWord)

    fun checkWord(wordInputText: String): CheckWordReturnType? {
        val letterList = wordInputText.split("").slice(1..wordInputText.length)
        if (" " !in letterList && letterList.size == (resWord.value?.length ?: -1)) {
            var word: ArrayList<LetterClass>? = arrayListOf()
            if (wordInputText.uppercase() == resWord.value) {
                for (letter in letterList) {
                    word?.add(LetterClass(letter.uppercase(), StatusLetterType.CORRECT))
                }
                val time = startTime.value
                winFlag.value = true
                if (time != null) {
                    val resTimeTemp = (Date().time - time) / 1000 / 60
                    resTime.value = resTimeTemp
                    return word?.let {
                        CheckWordReturnType(
                            it,
                            StatusCheckWord.WIN,
                            resTimeTemp
                        )
                    }
                }
            } else {
                word = processWord(letterList)
                if (startTime.value != null) {
                    val time = startTime.value
                    if (time != null) {
                        return word?.let {
                            CheckWordReturnType(
                                it,
                                StatusCheckWord.LOSE,
                                time.toDouble()
                            )
                        }
                    }
                }
            }
        }
        return null
    }

    fun finishGame(): FinishGameResult? {
        var time = startTime.value
        val res = winFlag.value
        val word = resWord.value
        if (winFlag.value == false) {
            if (time != null && res != null && word != null) {
                return FinishGameResult((Date().time.toDouble() - time) / 1000 / 60, res, word)
            }
        } else {
            time = resTime.value?.toDouble()
            if (time != null && res != null && word != null) {
                return FinishGameResult(time, res, word)
            }
        }
        return null
    }

    private fun processWord(wordInputText: List<String>): ArrayList<LetterClass>? {
        return resWord.value?.let { api.processWord(wordInputText, it) }
    }
}

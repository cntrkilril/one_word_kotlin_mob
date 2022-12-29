package com.example.oneword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oneword.databinding.FragmentGameBinding
import java.util.*

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val adapter = WordsAdapter()

    lateinit var wordInput: EditText
    lateinit var checkButton: Button
    lateinit var finishButton: Button
    lateinit var titleGameText: TextView

    var winFlag = false
    var resWord = ""
    var level = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val startTime = Date().time
        var countTime = Date().time

        init()
        setResWord()
        setLevel()
        setHintInInput("Введите слово из $level букв")

        checkButton.setOnClickListener {
            checkWord(startTime)
        }

        finishButton.setOnClickListener {
            finishGame(winFlag, startTime)
        }


        return view
    }

    fun init() {
        binding.wordList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.wordList.adapter = adapter
        wordInput = binding.wordInput
        checkButton = binding.checkButton
        finishButton = binding.finishButton
        titleGameText = binding.titleGame
    }

    fun setResWord() {
        resWord = (activity.let { (it as MainActivity).resWord }).uppercase()
    }

    fun setLevel() {
        level = ((activity?.let { (it as MainActivity).currentLevel }).toString())
    }

    fun setHintInInput(value: String) {
        wordInput.setHint(value)
    }

    fun finishGame(winFlag: Boolean, startTime: Long) {
        var countTime: Long = 0
        if (!winFlag) {
            countTime = Date().time - startTime
        }
        activity.let {
            (it as MainActivity).goToMainFragment()
            it.showMessage(getString(R.string.empty_text))
            it.addToStory(
                ResultGame(
                    winFlag,
                    resWord,
                    adapter.itemCount,
                    countTime.toDouble() / 1000 / 60
                )
            )
        }
    }

    fun checkWord(startTime: Long) {
        val wordInputText = wordInput.text.split("").slice(1..wordInput.text.length)
//          валидация слова
        if (" " !in wordInputText && wordInputText.size == resWord.length) {
            var word = arrayListOf<LetterClass>()
//              проверка на "точное попадание"
            if (wordInput.text.toString().uppercase() == resWord) {
                for (letter in wordInputText) {
                    word.add(LetterClass(letter.uppercase(), StatusLetterType.CORRECT))
                    titleGameText.text = "Победа! Время: ${
                        String.format("%.2f", (Date().time - startTime).toDouble() / 1000 / 60)
                    } мин"
                    wordInput.isEnabled = false
                    checkButton.isEnabled = false
                    finishButton.isVisible = true
                    winFlag = true
                }
            } else {
//                  обработка букв
                word = activity.let { (it as MainActivity).processWord(wordInputText) }
                activity.let {
                    (it as MainActivity).showMessage(getString(R.string.empty_text))
                }
            }
            wordInput.setText(getString(R.string.empty_text))
            adapter.add(word)
        } else {
            activity.let {
                (it as MainActivity).showMessage(getString(R.string.wrong_input))
            }
            wordInput.setText("")
        }
    }

    companion object {

        fun newInstance() = GameFragment()
    }
}
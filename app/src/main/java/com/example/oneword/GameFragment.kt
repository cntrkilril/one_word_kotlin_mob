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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val startTime = Date().time
        var countTime = Date().time

        var winFlag = false

        binding.wordList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.wordList.adapter = adapter

        val wordInput: EditText = binding.wordInput
        val checkButton: Button = binding.checkButton
        val finishButton: Button = binding.finishButton
        val titleGameText: TextView = binding.titleGame

        val resWord = (activity.let { (it as MainActivity).resWord }).uppercase()

        wordInput.setHint("Введите слово из ${((activity?.let { (it as MainActivity).currentLevel }).toString())} букв")

        checkButton.setOnClickListener {
            val wordInputText = wordInput.text.split("").slice(1..wordInput.text.length)
//          валидация слова
            if (" " !in wordInputText && wordInputText.size == resWord.length) {
                var word = arrayListOf<LetterClass>()
//              проверка на "точное попадание"
                if (wordInput.text.toString().uppercase() == resWord) {
                    for (letter in wordInputText) {
                        word.add(LetterClass(letter.uppercase(), "correct"))
                        countTime = Date().time - startTime
                        titleGameText.text = "Победа! Время: ${
                            String.format("%.2f", countTime.toDouble() / 1000 / 60)
                        } мин"
                        wordInput.isEnabled = false
                        checkButton.isEnabled = false
                        finishButton.isVisible = true
                        winFlag = true
                    }
                } else {
//                  обработка букв
                    word = Utils().processWord(wordInputText, resWord)
                    activity.let {
                        (it as MainActivity).showMessage("")
                    }
                }
                wordInput.setText("")
                adapter.add(word)
            } else {
                activity.let {
                    (it as MainActivity).showMessage("Неправильный ввод")
                }
                wordInput.setText("")
            }
        }

        finishButton.setOnClickListener {
            if (!winFlag) {
                countTime = Date().time - startTime
            }
            activity.let {
                (it as MainActivity).goToMainFragment()
                it.showMessage("")
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


        return view
    }

    companion object {

        fun newInstance() = GameFragment()
    }
}
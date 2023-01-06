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

    private var _model: GameViewModel? = null
    private val model get() = _model!!

    private lateinit var wordInput: EditText
    private lateinit var checkButton: Button
    private lateinit var finishButton: Button
    private lateinit var titleGameText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        _model =
            GameViewModel(
                (activity.let { (it as MainActivity).getResWord() })?.uppercase() ?: "",
                (activity.let { (it as MainActivity).getCurrentLevel() })?.uppercase() ?: ""
            )
        val view: View = binding.root

        init()
        setHintInInput("Введите слово из ${model.level.value} букв")

        checkButton.setOnClickListener {
            checkWord()
        }

        finishButton.setOnClickListener {
            finishGame()
        }


        return view
    }

    private fun init() {
        binding.wordList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.wordList.adapter = adapter
        wordInput = binding.wordInput
        checkButton = binding.checkButton
        finishButton = binding.finishButton
        titleGameText = binding.titleGame
    }

    private fun setHintInInput(value: String) {
        wordInput.setHint(value)
    }

    private fun finishGame() {
        val result = model.finishGame()
        activity.let {
            (it as MainActivity).goToMainFragment()
            it.showMessage(getString(R.string.empty_text))
            if (result != null) {
                it.addToStory(
                    ResultGame(
                        result.winFlag,
                        result.resWord,
                        adapter.itemCount,
                        result.time
                    )
                )
            }
        }
    }

    private fun checkWord() {
        if (wordInput.text.toString() == "" || wordInput.text.length != model.resWord.value?.length) {
            activity.let {
                (it as MainActivity).showMessage(getString(R.string.wrong_input))
            }
            wordInput.setText("")
        }
        val checkRes = model.checkWord(wordInput.text.toString())
        if (checkRes != null) {
            if (checkRes.status == StatusCheckWord.WIN) {
                "Победа! Время: ${
                    String.format("%.2f", checkRes.time)
                } мин".also { titleGameText.text = it }
                wordInput.isEnabled = false
                checkButton.isEnabled = false
                finishButton.isVisible = true
            } else {
                activity.let {
                    (it as MainActivity).showMessage(getString(R.string.empty_text))
                }
            }
        }
        wordInput.setText(getString(R.string.empty_text))
        if (checkRes != null) {
            adapter.add(checkRes.word)
        }
    }

    companion object {
        fun newInstance() = GameFragment()
    }
}
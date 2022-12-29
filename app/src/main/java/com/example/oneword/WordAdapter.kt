package com.example.oneword

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oneword.databinding.WordItemBinding

class WordsAdapter : RecyclerView.Adapter<WordsAdapter.WordHolder>() {

    val wordsList = ArrayList<ArrayList<LetterClass>>()


    class WordHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = WordItemBinding.bind(item)
        fun bind(word: ArrayList<LetterClass>, position: Int) {
            val lettersAdapter = LettersAdapter(word)
            binding.letterList.layoutManager = LinearLayoutManager(
                binding.letterList.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.letterList.adapter = lettersAdapter
            binding.numberWordText.text = (position + 1).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
        return WordHolder(view)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.bind(wordsList[position], position)
    }

    override fun getItemCount(): Int {
        return wordsList.size
    }

    fun add(word: ArrayList<LetterClass>) {
        wordsList.add(word)
        notifyDataSetChanged()
    }

}
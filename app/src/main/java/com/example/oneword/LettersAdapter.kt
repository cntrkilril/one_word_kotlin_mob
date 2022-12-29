package com.example.oneword

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oneword.databinding.LetterItemBinding

class LettersAdapter(var letters: List<LetterClass>) :
    RecyclerView.Adapter<LettersAdapter.LetterHolder>() {

    val letterList = letters

    class LetterHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = LetterItemBinding.bind(item)
        fun bind(letter: LetterClass) {
            binding.letterText.text = letter.letter
            when (letter.status) {
                "correct" -> binding.letterText.setBackgroundColor(Color.parseColor("#3AA52E"))
                "exist" -> binding.letterText.setBackgroundColor(Color.parseColor("#A1A52E"))
                "not exist" -> binding.letterText.setBackgroundColor(Color.parseColor("#424242"))
                else -> return
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.letter_item, parent, false)
        return LetterHolder(view)
    }

    override fun onBindViewHolder(holder: LetterHolder, position: Int) {
        holder.bind(letterList[position])
    }

    override fun getItemCount(): Int {
        return letterList.size
    }

//    fun addAll(letters: ArrayList<String>) {
//        letterList.clear()
//        letterList.addAll(letters)
//    }

}
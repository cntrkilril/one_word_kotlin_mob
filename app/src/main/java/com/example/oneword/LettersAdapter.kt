package com.example.oneword

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oneword.databinding.LetterItemBinding

class LettersAdapter(var letter: List<LetterClass>) :
    RecyclerView.Adapter<LettersAdapter.LetterHolder>() {

    var letterList = letter

    class LetterHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = LetterItemBinding.bind(item)
        fun bind(letter: LetterClass) {
            binding.letterText.text = letter.letter
            when (letter.status) {
                StatusLetterType.CORRECT -> binding.letterText.setBackgroundColor(
                    itemView.context.resources.getColor(
                        R.color.main_green
                    )
                )
                StatusLetterType.EXIST -> binding.letterText.setBackgroundColor(
                    itemView.context.resources.getColor(
                        R.color.main_yellow
                    )
                )
                StatusLetterType.NOT_EXIST -> binding.letterText.setBackgroundColor(
                    itemView.context.resources.getColor(
                        R.color.gray
                    )
                )
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

}
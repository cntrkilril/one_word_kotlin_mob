package com.example.oneword

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oneword.databinding.StoryItemBinding

class StoryAdapter :
    RecyclerView.Adapter<StoryAdapter.StoryHolder>() {

    private val resultsList = arrayListOf<ResultGame>()

    class StoryHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = StoryItemBinding.bind(item)
        fun bind(result: ResultGame) {
            binding.countAttemptsText.text = "Попыток: " + result.countAttempts.toString()
            binding.timeText.text = String.format("%.2f", result.countTime) + " мин"
            binding.wordText.text = result.word
            if (result.status) {
                binding.storyItem.setBackgroundColor(itemView.context.resources.getColor(R.color.main_green))
            } else {
                binding.storyItem.setBackgroundColor(itemView.context.resources.getColor(R.color.main_red))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false)
        return StoryHolder(view)
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        holder.bind(resultsList[position])
    }

    override fun getItemCount(): Int {
        return resultsList.size
    }

    fun addAll(results: ArrayList<ResultGame>) {
        resultsList.clear()
        resultsList.addAll(results)
    }

}
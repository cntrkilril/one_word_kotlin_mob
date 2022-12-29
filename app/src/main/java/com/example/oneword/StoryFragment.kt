package com.example.oneword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oneword.databinding.FragmentRulesBinding
import com.example.oneword.databinding.FragmentStoryBinding

class StoryFragment : Fragment() {
    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val story = activity.let { (it as MainActivity).getStoryByCurrentUser() }

        val adapter = StoryAdapter()
        binding.storyList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.storyList.adapter = adapter

        adapter.addAll(story)

        if (story.size == 0) {
            activity.let { (it as MainActivity).showMessage("История пока пуста") }
        }

        return view
    }

    companion object {

        fun newInstance() = StoryFragment()
    }
}
package com.example.oneword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oneword.databinding.FragmentStoryBinding

class StoryFragment : Fragment() {
    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!

    private var _model: StoryViewModel? = null
    private val model get() = _model!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        _model = StoryViewModel(activity.let { (it as MainActivity).getCurrentUser() })
        val view: View = binding.root

        model.getStoryByCurrentUser()

        val adapter = StoryAdapter()
        binding.storyList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.storyList.adapter = adapter

        model.story.observe(viewLifecycleOwner) { newStory ->
            if (newStory.size == 0) {
                activity.let { (it as MainActivity).showMessage(getString(R.string.empty_story_message)) }
            }
            adapter.addAll(newStory)
        }

        return view
    }

    companion object {
        fun newInstance() = StoryFragment()
    }
}
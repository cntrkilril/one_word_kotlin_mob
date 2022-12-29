package com.example.oneword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.oneword.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val logoutButton: Button = binding.logoutButton
        val aboutButton: Button = binding.aboutButton
        val storyButton: Button = binding.storyButton

        val hardLevelButton: Button = binding.hardLevelButton
        val mediumLevelButton: Button = binding.mediumLevelButton
        val easyLevelButton: Button = binding.easyLevelButton

        logoutButton.setOnClickListener {
            activity.let {
                (it as MainActivity).logout()
            }
        }

        aboutButton.setOnClickListener {
            activity.let {
                (it as MainActivity).goToAboutFragment()
            }
        }

        storyButton.setOnClickListener {
            activity.let {
                (it as MainActivity).goToStoryFragment()
            }
        }

        hardLevelButton.setOnClickListener {
            activity.let {
                (it as MainActivity).goToGameFragment(0)
            }
        }

        mediumLevelButton.setOnClickListener {
            activity.let {
                (it as MainActivity).goToGameFragment(1)
            }
        }

        easyLevelButton.setOnClickListener {
            activity.let {
                (it as MainActivity).goToGameFragment(2)
            }
        }

        return view
    }

    companion object {

        fun newInstance() = MainFragment()
    }
}
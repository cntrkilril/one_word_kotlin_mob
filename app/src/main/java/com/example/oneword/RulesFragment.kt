package com.example.oneword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oneword.databinding.FragmentRulesBinding

class RulesFragment : Fragment() {

    private var _binding: FragmentRulesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRulesBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        fun newInstance() = RulesFragment()
    }
}
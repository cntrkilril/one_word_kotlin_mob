package com.example.oneword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.oneword.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val loginButton: Button = binding.loginButton
        val registerButton: Button = binding.registerButton
        val loginInput: EditText = binding.loginInput
        val passwordInput: EditText = binding.passwordInput

        loginButton.setOnClickListener {
            activity?.let {
                (it as MainActivity).login(
                    loginInput.text.toString(),
                    passwordInput.text.toString()
                )
            }
            loginInput.setText("")
            passwordInput.setText("")
        }

        registerButton.setOnClickListener {
            activity?.let {
                (it as MainActivity).register(
                    loginInput.text.toString(),
                    passwordInput.text.toString()
                )
                loginInput.setText("")
                passwordInput.setText("")
            }
        }


        return view
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}
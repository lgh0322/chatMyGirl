package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

    lateinit var binding:FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentLoginBinding.inflate(inflater,container,false)

        binding.hint.setOnClickListener {
            binding.hint.isEnabled=false
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return binding.root
    }
}
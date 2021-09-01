package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentSignUpBinding

class SignUpFragment: Fragment() {

    lateinit var binding:FragmentSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentSignUpBinding.inflate(inflater,container,false)


        return binding.root
    }
}
package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentSignUpBinding

class SignUpFragment: Fragment() {

    lateinit var binding:FragmentSignUpBinding

    fun isReady():Boolean{
        if(binding.x1.text.toString().isNullOrEmpty()){
            return false
        }
        if(binding.x2.text.toString().isNullOrEmpty()){
            return false
        }
        return true
    }

    fun changeBt(){
        if(isReady()){
            binding.signUp.apply {
                background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.login_button)

            }
        }else{
            binding.signUp.apply {
                background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.login_button_before)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentSignUpBinding.inflate(inflater,container,false)

        binding.x1.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                changeBt()
            }

        })

        binding.x2.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                changeBt()
            }

        })

        binding.signUp.setOnClickListener {

        }

        return binding.root
    }
}
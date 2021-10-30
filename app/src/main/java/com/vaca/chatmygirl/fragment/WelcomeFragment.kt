package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.data.MyStorage
import com.vaca.chatmygirl.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class WelcomeFragment:Fragment() {



    lateinit var binding:FragmentWelcomeBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentWelcomeBinding.inflate(inflater,container,false)

//        findNavController().navigate(R.id.action_welcomeFragment_to_mainFragment)


        MainScope().launch {
            delay(3000)
            val yes= MyStorage.getAccount()
            if(yes.user.isEmpty()){
                try {
                    findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
                }catch (e:Exception){

                }

            }else{
                try {
                    findNavController().navigate(R.id.action_welcomeFragment_to_mainFragment)
                }catch (e:Exception){

                }

            }
        }


        return binding.root
    }
}
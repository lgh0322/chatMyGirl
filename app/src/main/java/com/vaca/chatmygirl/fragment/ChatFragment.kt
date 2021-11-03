package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentChatBinding
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding
import com.vaca.chatmygirl.net.NetCmd

class ChatFragment: Fragment() {

    lateinit var binding:FragmentChatBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentChatBinding.inflate(inflater,container,false)
        binding.messageSend.setOnClickListener {
            val text=binding.chatMessage.text.toString()
            if(text.isNotEmpty()){
                NetCmd.chatText(text,false)
            }
        }

        return binding.root
    }
}
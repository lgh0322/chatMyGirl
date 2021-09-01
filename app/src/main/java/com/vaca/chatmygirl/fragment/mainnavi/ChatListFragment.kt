package com.vaca.chatmygirl.fragment.mainnavi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentChatListBinding
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding

class ChatListFragment: Fragment() {

    lateinit var binding:FragmentChatListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentChatListBinding.inflate(inflater,container,false)


        return binding.root
    }
}
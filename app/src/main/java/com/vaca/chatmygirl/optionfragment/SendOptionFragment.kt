package com.vaca.chatmygirl.optionfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentHistoryBinding
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding
import com.vaca.chatmygirl.databinding.FragmentSendOptionBinding

class SendOptionFragment: Fragment() {

    lateinit var binding:FragmentSendOptionBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentSendOptionBinding.inflate(inflater,container,false)


        return binding.root
    }
}
package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentChat2Binding
import com.vaca.chatmygirl.databinding.FragmentChatBinding
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding
import com.vaca.chatmygirl.net.NetCmd
import com.vaca.chatmygirl.utils.SoftInputUtil

import android.R
import com.vaca.chatmygirl.utils.SoftInputUtil.ISoftInputChanged


class ChatFragment: Fragment() {

    lateinit var binding:FragmentChat2Binding


    private fun attachView() {

        //editText2为需要调整的View
        val editText2 = binding.et2
        val softInputUtil = SoftInputUtil()
        softInputUtil.attachSoftInput(
            editText2
        ) { isSoftInputShow, softInputHeight, viewOffset ->
            if (isSoftInputShow) {
                editText2.setTranslationY(binding.et2.getTranslationY() - viewOffset)
            } else {
                editText2.setTranslationY(0f)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentChat2Binding.inflate(inflater,container,false)
        attachView()
      /*  binding.messageSend.setOnClickListener {
            val text=binding.chatMessage.text.toString()
            if(text.isNotEmpty()){
                NetCmd.chatText(text,false)
            }
        }*/

        return binding.root
    }
}
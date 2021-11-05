package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentChatBinding
import com.vaca.chatmygirl.utils.SoftInputUtil


class ChatFragment: Fragment() {

    lateinit var binding:FragmentChatBinding


    private fun attachView() {

        //editText2为需要调整的View
        val editText = binding.constraintLayout
        val softInputUtil = SoftInputUtil()
        softInputUtil.attachSoftInput(
            editText
        ) { isSoftInputShow, softInputHeight, viewOffset ->
            if (isSoftInputShow) {
                editText.setTranslationY(binding.constraintLayout.getTranslationY() - viewOffset)
            } else {
                editText.setTranslationY(0f)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentChatBinding.inflate(inflater,container,false)
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
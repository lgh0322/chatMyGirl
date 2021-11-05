package com.vaca.chatmygirl.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.data.MyStorage
import com.vaca.chatmygirl.databinding.FragmentChatBinding
import com.vaca.chatmygirl.net.NetCmd
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
//                binding.container.visibility=View.GONE
                val lp=binding.container.layoutParams
                val myHeight=binding.constraintLayout.getTranslationY() - viewOffset
                MyStorage.setKeyboardHeight(-myHeight.toInt())
                lp.height=-myHeight.toInt()
                binding.container.layoutParams=lp
                Log.e("gogo",myHeight.toString())
//                editText.setTranslationY(myHeight)
//                editText.setTranslationY(0f)
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
        val lp=binding.container.layoutParams
        lp.height=MyStorage.keyboardHeightX
        binding.container.layoutParams=lp

        attachView()
        binding.messageSend.setOnClickListener {
            val text=binding.chatMessage.text.toString()
            if(text.isNotEmpty()){
                NetCmd.chatText(text,false)
            }
        }

        binding.plus.setOnClickListener {
            (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(requireActivity().getCurrentFocus()?.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        binding.chatMessage.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val text=binding.chatMessage.text.toString()
                if(text.isEmpty()){
                    binding.messageSend.visibility=View.GONE
                }else{
                    binding.messageSend.visibility=View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        return binding.root
    }
}
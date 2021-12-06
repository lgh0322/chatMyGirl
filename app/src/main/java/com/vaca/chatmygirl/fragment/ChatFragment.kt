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
import com.vaca.chatmygirl.utils.SoftHeight
import com.vaca.chatmygirl.utils.SoftInputUtil


class ChatFragment: Fragment() {

    lateinit var binding:FragmentChatBinding

    var keyboardVisible=false

    fun showKeyboard(view: View) {
        val imm = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.requestFocus()
        imm.showSoftInput(view, 0)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentChatBinding.inflate(inflater,container,false)

        val editText = binding.constraintLayout

        val lp=binding.container.layoutParams
        lp.height=MyStorage.keyboardHeightX
        binding.container.layoutParams=lp

//        attachView()

        SoftHeight.observeSoftKeyboard(requireActivity(),object:SoftHeight.OnSoftKeyboardChangeListener{
            override fun onSoftKeyBoardChange(softKeybardHeight: Int, visible: Boolean) {
                Log.e("gogo",softKeybardHeight.toString())
                if(visible){
                    val lp=binding.container.layoutParams
                    MyStorage.setKeyboardHeight(softKeybardHeight)
                    lp.height=softKeybardHeight
                    binding.container.layoutParams=lp
                    if(binding.container.visibility==View.VISIBLE){
                        editText.setTranslationY(0f)
                    }else{
                        editText.setTranslationY(-softKeybardHeight.toFloat())
                    }

                }else{


                }
                keyboardVisible=visible
            }

        })
        binding.messageSend.setOnClickListener {
            val text=binding.chatMessage.text.toString()
            if(text.isNotEmpty()){
                NetCmd.chatText(text,false)
            }
        }

        binding.plus.setOnClickListener {
            if(keyboardVisible){
                    binding.container.visibility=View.VISIBLE
                (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(requireActivity().getCurrentFocus()?.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }else{
                Log.e("plus","start")
                if(binding.container.visibility==View.VISIBLE){
                    Log.e("plus","start1")
                    showKeyboard(binding.chatMessage)
                }else{
                    Log.e("plus","start2")
                    binding.container.visibility=View.VISIBLE
                }

            }
        }

        binding.voice.setOnClickListener {
            if(keyboardVisible){
                binding.container.visibility=View.VISIBLE
                (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(requireActivity().getCurrentFocus()?.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }else{
                Log.e("plus","start")
                if(binding.container.visibility==View.VISIBLE){
                    Log.e("plus","start1")
                    showKeyboard(binding.chatMessage)
                }else{
                    Log.e("plus","start2")
                    binding.container.visibility=View.VISIBLE
                }

            }
        }

        binding.emotion.setOnClickListener {
            if(keyboardVisible){
                binding.container.visibility=View.VISIBLE
                (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(requireActivity().getCurrentFocus()?.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }else{
                Log.e("plus","start")
                if(binding.container.visibility==View.VISIBLE){
                    Log.e("plus","start1")
                    showKeyboard(binding.chatMessage)
                }else{
                    Log.e("plus","start2")
                    binding.container.visibility=View.VISIBLE
                }

            }
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
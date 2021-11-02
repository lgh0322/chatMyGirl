package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.LiveEvent
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentSignUpBinding
import com.vaca.chatmygirl.net.NetCmd

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
            isReadyGo=true
            binding.signUp.apply {
                background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.login_button)

            }
        }else{
            isReadyGo=false
            binding.signUp.apply {
                background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.login_button_before)
            }
        }
    }

    var isReadyGo=false





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        this.inflater=inflater
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
            if (isReadyGo){
                val user=binding.x1.text.toString()
                val pass=binding.x2.text.toString()
                NetCmd.signUp(user,pass)

            }
        }

        LiveEventBus.get("signUp", String::class.java).observe(viewLifecycleOwner,{
            if(it=="0"){
                Log.e("girlxx","注册成功")
                bindSet(true)
                findNavController().navigateUp()
            }else{
                Log.e("girlxx","注册失败， 用户名已存在")
                bindSet(false)
            }

        })

        return binding.root
    }

    lateinit var inflater: LayoutInflater
    fun bindSet(boolean: Boolean) {
        Toast(requireContext()).apply {
            val layout = inflater.inflate(R.layout.toast_bind_layout, null)
            if (boolean) {
                layout.findViewById<TextView>(R.id.dada).apply {
                    text = "注册成功"
                }
                layout.findViewById<ImageView>(R.id.gr).setImageResource(R.drawable.success_icon)
            } else {
                layout.findViewById<TextView>(R.id.dada).apply {
                    text = "注册失败， 用户名已存在"
                }
                layout.findViewById<ImageView>(R.id.gr).setImageResource(R.drawable.failure_icon)
            }
            setGravity(Gravity.CENTER, 0, 0)
            duration = Toast.LENGTH_SHORT
            setView(layout)
            show()
        }
    }
}
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
import androidx.navigation.fragment.findNavController
import com.jeremyliao.liveeventbus.LiveEventBus
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.bean.Account
import com.vaca.chatmygirl.data.MyStorage
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.net.NetCmd

class LoginFragment: Fragment() {

    lateinit var binding:FragmentLoginBinding
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
            binding.login.apply {
                background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.login_button)

            }
        }else{
            isReadyGo=false
            binding.login.apply{
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

        binding= FragmentLoginBinding.inflate(inflater,container,false)

        binding.hint.setOnClickListener {
            binding.hint.isEnabled=false
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding.x1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                changeBt()
            }

        })

        binding.x2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                changeBt()
            }

        })

        binding.login.setOnClickListener {
            if (isReadyGo){
                val user=binding.x1.text.toString()
                val pass=binding.x2.text.toString()
                NetCmd.login(user,pass)

            }
        }

        LiveEventBus.get("login", String::class.java).observe(viewLifecycleOwner,{
            if(it=="0"){
                val user=binding.x1.text.toString()
                val pass=binding.x2.text.toString()
                MyStorage.setAccount(Account(user,pass))
                Log.e("girlxx","登陆成功")
                bindSet(true)
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }else{
                Log.e("girlxx","登陆失败， 密码错误")
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
                    text = "登陆成功"
                }
                layout.findViewById<ImageView>(R.id.gr).setImageResource(R.drawable.success_icon)
            } else {
                layout.findViewById<TextView>(R.id.dada).apply {
                    text = "登陆失败， 密码错误"
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
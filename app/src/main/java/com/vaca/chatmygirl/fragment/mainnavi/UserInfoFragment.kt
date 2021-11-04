package com.vaca.chatmygirl.fragment.mainnavi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding
import com.vaca.chatmygirl.databinding.FragmentMyBinding
import com.vaca.chatmygirl.databinding.FragmentUserInfoBinding

class UserInfoFragment: Fragment() {

    lateinit var binding:FragmentUserInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding=FragmentUserInfoBinding.inflate(inflater,container,false)


        return binding.root
    }
}
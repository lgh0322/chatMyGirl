package com.vaca.chatmygirl.fragment.mainnavi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding
import com.vaca.chatmygirl.databinding.FragmentMyBinding

class MyFragment: Fragment() {

    lateinit var binding:FragmentMyBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentMyBinding.inflate(inflater,container,false)


        return binding.root
    }
}
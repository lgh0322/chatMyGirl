package com.vaca.chatmygirl.fragment.mainnavi.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding
import com.vaca.chatmygirl.databinding.FragmentPersonListBinding

class PersonListFragment: Fragment() {

    lateinit var binding:FragmentPersonListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentPersonListBinding.inflate(inflater,container,false)


        return binding.root
    }
}
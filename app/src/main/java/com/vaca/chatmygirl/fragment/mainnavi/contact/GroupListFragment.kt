package com.vaca.chatmygirl.fragment.mainnavi.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentGroupListBinding
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding

class GroupListFragment: Fragment() {

    lateinit var binding:FragmentGroupListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentGroupListBinding.inflate(inflater,container,false)
        binding.bar.setTextView(binding.dialog)
        binding.bar.setOnTouchingLetterChangedListener {

        }

        return binding.root
    }
}
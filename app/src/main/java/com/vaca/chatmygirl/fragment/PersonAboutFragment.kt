package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentPersonAboutBinding

class PersonAboutFragment : Fragment() {

    lateinit var binding: FragmentPersonAboutBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentPersonAboutBinding.inflate(inflater, container, false)


        return binding.root
    }
}
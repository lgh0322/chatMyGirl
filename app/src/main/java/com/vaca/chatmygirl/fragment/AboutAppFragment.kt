package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentAboutAppBinding

class AboutAppFragment : Fragment() {

    lateinit var binding: FragmentAboutAppBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentAboutAppBinding.inflate(inflater, container, false)


        return binding.root
    }
}
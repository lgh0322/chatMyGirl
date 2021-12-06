package com.vaca.chatmygirl.optionfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentFaceBinding

class FaceFragment : Fragment() {

    lateinit var binding: FragmentFaceBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentFaceBinding.inflate(inflater, container, false)


        return binding.root
    }
}
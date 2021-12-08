package com.vaca.chatmygirl.chatfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.data.MyStorage
import com.vaca.chatmygirl.databinding.FragmentVideoDetailBinding
import com.vaca.chatmygirl.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class VideoDetailFragment : Fragment() {


    lateinit var binding: FragmentVideoDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoDetailBinding.inflate(inflater, container, false)




        return binding.root
    }
}
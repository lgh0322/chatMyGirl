package com.vaca.chatmygirl.chatfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.data.MyStorage
import com.vaca.chatmygirl.databinding.FragmentImageDetailBinding
import com.vaca.chatmygirl.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ImageDetailFragment : Fragment() {


    lateinit var binding: FragmentImageDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentImageDetailBinding.inflate(inflater, container, false)



        return binding.root
    }
}
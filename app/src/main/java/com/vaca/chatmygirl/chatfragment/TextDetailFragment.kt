package com.vaca.chatmygirl.chatfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.data.MyStorage
import com.vaca.chatmygirl.databinding.FragmentFileDetailBinding
import com.vaca.chatmygirl.databinding.FragmentTextDetailBinding
import com.vaca.chatmygirl.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TextDetailFragment : Fragment() {


    lateinit var binding: FragmentTextDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTextDetailBinding.inflate(inflater, container, false)


        return binding.root
    }
}
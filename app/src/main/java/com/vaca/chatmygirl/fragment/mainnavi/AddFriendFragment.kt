package com.vaca.chatmygirl.fragment.mainnavi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.databinding.FragmentAddFriendBinding

class AddFriendFragment : Fragment() {

    lateinit var binding: FragmentAddFriendBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentAddFriendBinding.inflate(inflater, container, false)




        return binding.root
    }
}
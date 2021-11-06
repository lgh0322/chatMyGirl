package com.vaca.chatmygirl.fragment.mainnavi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.databinding.FragmentAddFriendBinding
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding
import com.vaca.chatmygirl.databinding.FragmentMyBinding
import com.vaca.chatmygirl.event.GoGo
import com.vaca.chatmygirl.net.NetCmd
import com.vaca.chatmygirl.utils.PathUtil
import java.io.File
import java.lang.Exception

class AddFriendFragment: Fragment() {

    lateinit var binding:FragmentAddFriendBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentAddFriendBinding.inflate(inflater,container,false)




        return binding.root
    }
}
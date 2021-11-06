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
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding
import com.vaca.chatmygirl.databinding.FragmentMyBinding
import com.vaca.chatmygirl.event.GoGo
import java.lang.Exception

class MyFragment: Fragment() {

    lateinit var binding:FragmentMyBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentMyBinding.inflate(inflater,container,false)

        binding.changeInfo.setOnClickListener {
            findNavController().navigate(R.id.action_myFragment_to_userInfoFragment)
        }

        binding.rlSetting2.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireContext().packageName, null)
            intent.data = uri
            try {
                requireContext().startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        binding.btLogout.setOnClickListener {
            GoGo.goLogin()
        }

        return binding.root
    }
}
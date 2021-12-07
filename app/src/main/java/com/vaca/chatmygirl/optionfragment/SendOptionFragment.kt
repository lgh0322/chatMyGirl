package com.vaca.chatmygirl.optionfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.scrat.app.selectorlibrary.ImageSelector
import com.vaca.chatmygirl.activity.MainActivity
import com.vaca.chatmygirl.activity.MainActivity.Companion.MAX_SELECT_COUNT
import com.vaca.chatmygirl.databinding.FragmentSendOptionBinding

class SendOptionFragment : Fragment() {

    lateinit var binding: FragmentSendOptionBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentSendOptionBinding.inflate(inflater, container, false)

        binding.camera.setOnClickListener {

        }

        binding.photo.setOnClickListener {
            ImageSelector.show(
                requireActivity(),
              MainActivity.REQUEST_CODE_SELECT_IMG,
               MAX_SELECT_COUNT
            )
        }

        binding.file.setOnClickListener {

        }


        binding.video.setOnClickListener {

        }


        return binding.root
    }
}
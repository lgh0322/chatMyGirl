package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.adapter.BookViewAdapter
import com.vaca.chatmygirl.data.MyStorage
import com.vaca.chatmygirl.databinding.FragmentTxtBookBinding
import com.vaca.chatmygirl.databinding.FragmentWelcomeBinding
import com.vaca.chatmygirl.net.FileCmd
import kotlinx.coroutines.*
import org.json.JSONArray
import java.io.File
import java.lang.Exception

class TxtBookFragment:Fragment() {



    lateinit var binding:FragmentTxtBookBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentTxtBookBinding.inflate(inflater,container,false)


        FileCmd.bookUrl.observe(viewLifecycleOwner,{
            binding.pdfView.fromFile(File(it)).load()
        })

        return binding.root
    }
}
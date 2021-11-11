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
import com.vaca.chatmygirl.databinding.FragmentWelcomeBinding
import com.vaca.chatmygirl.net.FileCmd
import com.vaca.chatmygirl.net.NetCmd
import kotlinx.coroutines.*
import org.json.JSONArray
import java.lang.Exception

class WelcomeFragment:Fragment() {



    lateinit var binding:FragmentWelcomeBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentWelcomeBinding.inflate(inflater,container,false)

        binding.bleTable.layoutManager = GridLayoutManager(requireContext(), 2);
        val bleViewAdapter = BookViewAdapter(requireContext())
        binding.bleTable.adapter = bleViewAdapter
        bleViewAdapter.setClickListener(object:BookViewAdapter.ItemClickListener{
            override fun onScanItemClick(name: String) {
                FileCmd.dataScope.launch {
                    try {
                        FileCmd.getFile(name,object:FileCmd.OnDownloadListener{
                            override fun onDownloadSuccess(filePath: String) {
                               Log.e("download","ok")
                                FileCmd.bookUrl.postValue(filePath)
                               MainScope().launch {
                                   findNavController().navigate(R.id.action_welcomeFragment_to_txtBookFragment)
                               }
                            }

                            override fun onDownloading(progress: Int) {

                            }

                            override fun onDownloadFailed() {
                                Log.e("download","fail")
                            }

                        })
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }

        })

        FileCmd.dataScope.launch {
            try {
                val n1=FileCmd.getTxtList()
                val n2=JSONArray(n1)
                val n3=n2.length()
                Log.e("sdf",n3.toString())
                withContext(Dispatchers.Main){
                    bleViewAdapter.addAllDevice(n2)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }

        }

        return binding.root
    }
}
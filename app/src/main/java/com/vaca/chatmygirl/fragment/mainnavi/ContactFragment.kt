package com.vaca.chatmygirl.fragment.mainnavi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaca.chatmygirl.adapter.ContactButtonAdapter
import com.vaca.chatmygirl.adapter.RecordButtonAdapter
import com.vaca.chatmygirl.databinding.FragmentContactBinding
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding

class ContactFragment: Fragment() {

    lateinit var binding:FragmentContactBinding
    companion object {
        val initJump = MutableLiveData<Int>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initJump.postValue(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentContactBinding.inflate(inflater,container,false)
        val buttonAdapter =ContactButtonAdapter(requireContext())
        val lm = object : LinearLayoutManager(requireContext(), HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        binding.topButton.layoutManager = lm
        buttonAdapter.addAll(arrayOf("朋友", "群组"))
        binding.topButton.adapter=buttonAdapter

        initJump.observe(viewLifecycleOwner,{
            buttonAdapter.setSelect(it)
        })
        return binding.root
    }
}
package com.vaca.chatmygirl.fragment.mainnavi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaca.chatmygirl.adapter.ReportEcgDataAdapter
import com.vaca.chatmygirl.bean.ChatListBean
import com.vaca.chatmygirl.databinding.FragmentChatListBinding
import com.vaca.chatmygirl.view.SpaceItemDecoration

class ChatListFragment : Fragment() {

    lateinit var binding: FragmentChatListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentChatListBinding.inflate(inflater, container, false)


        val myEcgItemAdapter = ReportEcgDataAdapter(requireContext())

        binding.list.apply {
            addItemDecoration(SpaceItemDecoration(10))
            layoutManager =
                LinearLayoutManager(requireContext()).apply { orientation = RecyclerView.VERTICAL }
            adapter = myEcgItemAdapter
        }

        myEcgItemAdapter.setList(listOf(ChatListBean("gaga", "123", "456", "789", 2344)))

        return binding.root
    }
}
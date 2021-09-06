package com.vaca.chatmygirl.fragment.mainnavi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaca.chatmygirl.ContactInputEvent
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.adapter.ContactButtonAdapter
import com.vaca.chatmygirl.databinding.FragmentContactBinding
import org.greenrobot.eventbus.EventBus

class ContactFragment : Fragment() {

    lateinit var binding: FragmentContactBinding

    companion object {
        var currentIndex = 0
        val initJump = MutableLiveData<Int>()
    }


    val topId = arrayOf(
        R.id.personListFragment,
        R.id.groupListFragment,
    )
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentContactBinding.inflate(inflater, container, false)

        binding.filterEdit.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                EventBus.getDefault().post(ContactInputEvent(p0.toString()))
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        val fm = childFragmentManager.findFragmentById(R.id.bx) as NavHostFragment
        navController = fm.navController
        val graph = navController.navInflater.inflate(R.navigation.contact_navigation)


        val buttonAdapter = ContactButtonAdapter(requireContext())
        val lm = object : LinearLayoutManager(requireContext(), HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        binding.topButton.layoutManager = lm
        buttonAdapter.addAll(arrayOf("朋友", "群组"))
        buttonAdapter.myGo = object : ContactButtonAdapter.WantInfo {
            override fun go(u: Int) {
                if (currentIndex == u) {
                    return
                }
                currentIndex = u
                if (navController.popBackStack(topId[u], false)) {

                } else {
                    navController.navigate(topId[u])
                }

            }

        }
        binding.topButton.adapter = buttonAdapter

        if (initJump.value == null) {
            initJump.postValue(currentIndex)
        }

        initJump.observe(viewLifecycleOwner, {
            graph.startDestination = topId[it]
            navController.graph = graph
            buttonAdapter.setSelect(it)
        })
        return binding.root
    }


    override fun onStop() {
        super.onStop()
        initJump.postValue(currentIndex)
    }
}
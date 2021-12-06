package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.adapter.RecordButtonAdapter
import com.vaca.chatmygirl.bean.MyInfo
import com.vaca.chatmygirl.databinding.FragmentMainBinding
import com.vaca.chatmygirl.net.FileCmd
import com.vaca.chatmygirl.net.NetCmd
import com.vaca.chatmygirl.utils.PathUtil
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    companion object {
        val initJump = MutableLiveData<Int>()
    }

    val topId = arrayOf(
        R.id.chatListFragment,
        R.id.contactFragment,
        R.id.myFragment
    )
    var currentIndex = 0
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentMainBinding.inflate(inflater, container, false)
        val fm = childFragmentManager.findFragmentById(R.id.bx) as NavHostFragment
        navController = fm.navController
        val graph = navController.navInflater.inflate(R.navigation.main_navigation)

        val buttonAdapter = RecordButtonAdapter(requireContext())
        val lm = object : LinearLayoutManager(requireContext(), HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        binding.topButton.layoutManager = lm
        buttonAdapter.addAll(arrayOf("消息", "朋友", "我的"))
        buttonAdapter.myGo = object : RecordButtonAdapter.WantInfo {
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
            initJump.value = 0
        }

        initJump.observe(viewLifecycleOwner, {
            currentIndex = it
            graph.startDestination = topId[it]
            navController.graph = graph
            buttonAdapter.setSelect(it)
        })




        NetCmd.dataScope.launch {
            try {
                val info = NetCmd.getInfo()
                val info2 = JSONObject(info)
                val info3 = info2.getJSONObject("data")
                val name = info3.getString("name")
                val avatar = info3.getString("avatar")
                if (!File(PathUtil.getPathX(avatar)).exists()) {
                    FileCmd.getFile(avatar, object : FileCmd.OnDownloadListener {
                        override fun onDownloadSuccess(filePath: String?) {
                            Log.e("getAvatar", "ues")
                            NetCmd.myInfo.postValue(MyInfo(name, avatar))
                        }

                        override fun onDownloading(progress: Int) {

                        }

                        override fun onDownloadFailed() {

                        }

                    })
                } else {
                    NetCmd.myInfo.postValue(MyInfo(name, avatar))
                }

            } catch (e: Exception) {

            }

        }

        return binding.root
    }
}
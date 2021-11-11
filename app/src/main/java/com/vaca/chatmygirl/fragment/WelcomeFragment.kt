package com.vaca.chatmygirl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.vaca.chatmygirl.adapter.BookViewAdapter
import com.vaca.chatmygirl.databinding.FragmentWelcomeBinding
import com.vaca.chatmygirl.net.FileCmd
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class WelcomeFragment : Fragment() {


    lateinit var binding: FragmentWelcomeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        binding.bleTable.layoutManager = GridLayoutManager(requireContext(), 2);
        val bleViewAdapter = BookViewAdapter(requireContext())
        binding.bleTable.adapter = bleViewAdapter
        bleViewAdapter.setClickListener(object : BookViewAdapter.ItemClickListener {
            override fun onScanItemClick(name: String) {
                /*       FileCmd.dataScope.launch {
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
                       }*/
            }

        })

        FileCmd.dataScope.launch {
            try {
//                FileCmd.getTxtList()
                val doc = Jsoup.connect("http://157.7.135.42/books").get();
                val trs = doc.select("table").select("tr")
                for (k in 0 until trs.size) {
                    val tds = trs.get(k).select("td")
                    for (j in 0 until tds.size) {
                        val text = tds.get(j).text()
                        val a=tds.get(j).select("a").attr("href")
                        Log.e(k.toString() +"fuck"+j, text+"   "+a)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return binding.root
    }
}
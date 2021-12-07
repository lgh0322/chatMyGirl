package com.vaca.chatmygirl.optionfragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.scrat.app.selectorlibrary.activity.ImageSelectorActivity
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.activity.VideoListActivity
import com.vaca.chatmygirl.databinding.FragmentSendOptionBinding
import com.vaca.chatmygirl.utils.PathUtil
import java.io.File

class SendOptionFragment : Fragment() {

    lateinit var binding: FragmentSendOptionBinding

    lateinit var takePhotoLauncher: ActivityResultLauncher<Intent>

    fun takePhoto(){
        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
        val fileJA: File = File(PathUtil.getPathX("fuck.jpg"))
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "com.vaca.chatmygirl.fileProvider",
            fileJA
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
        takePhotoLauncher.launch(intent)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSendOptionBinding.inflate(inflater, container, false)




        takePhotoLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) {
            Log.e("gugu3","yues")
        }



        val requestPhotoPermission= registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            val grantedList = it.filterValues { it }.mapNotNull { it.key }
            val allGranted = grantedList.size == it.size
            if(allGranted){
               takePhoto()
            }
        }


        binding.camera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                requestPhotoPermission.launch( arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))

                return@setOnClickListener
            }
            takePhoto()
        }


        val phoneSelector=registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) {
            Log.e("gug2u","yues")
        }
        binding.photo.setOnClickListener {
            val i = Intent(
                activity,
                ImageSelectorActivity::class.java
            )
            i.putExtra(ImageSelectorActivity.EXTRA_KEY_MAX, 10)
            phoneSelector.launch(i)
        }

        val openFileSelect= registerForActivityResult(ActivityResultContracts.OpenDocument()){
//                Glide.with(this).load(it).into(binding.imageView)
        }
        binding.file.setOnClickListener {
           openFileSelect.launch(arrayOf("text/plain"))
        }


        val takeVideo=registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) {
            Log.e("gugu","yues")
        }

        val selectVideo= registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) {
            Log.e("gug2u","yues")
        }
        binding.video.setOnClickListener {
            val builder_video = AlertDialog.Builder(requireContext()) //创建对话框
            val layout_video = layoutInflater.inflate(R.layout.dialog_select_video, null) //获取自定义布局
            builder_video.setView(layout_video) //设置对话框的布局
            val dialog_video = builder_video.create() //生成最终的对话框
            dialog_video.show() //显示对话框
            val takeVideoTV = layout_video.findViewById<TextView>(R.id.videoGraph)
            val chooseVideoTV = layout_video.findViewById<TextView>(R.id.video)
            val cancelVideoTV = layout_video.findViewById<TextView>(R.id.cancel_video)
            //设置监听
            takeVideoTV.setOnClickListener {
                dialog_video.dismiss()
                val intentx = Intent(MediaStore.ACTION_VIDEO_CAPTURE) // 创建一个请求视频的意图
                intentx.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1) // 设置视频的质量，值为0-1，
                intentx.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20) // 设置视频的录制长度，s为单位
                intentx.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 50 * 1024 * 1024L) // 设置视频文件大小，字节为单位
                takeVideo.launch(intentx)

            }
            chooseVideoTV.setOnClickListener {
                dialog_video.dismiss()
                val intent: Intent = Intent(requireActivity(), VideoListActivity::class.java)
                selectVideo.launch(intent)
            }
            cancelVideoTV.setOnClickListener {
                dialog_video.dismiss()
            }
        }


        return binding.root
    }


}
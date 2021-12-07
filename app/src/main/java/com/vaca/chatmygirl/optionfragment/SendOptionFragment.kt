package com.vaca.chatmygirl.optionfragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.scrat.app.selectorlibrary.ImageSelector
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.activity.MainActivity
import com.vaca.chatmygirl.activity.MainActivity.Companion.MAX_SELECT_COUNT
import com.vaca.chatmygirl.activity.VideoListActivity
import com.vaca.chatmygirl.databinding.FragmentSendOptionBinding
import com.vaca.chatmygirl.utils.PathUtil
import java.io.File

class SendOptionFragment : Fragment() {

    lateinit var binding: FragmentSendOptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentSendOptionBinding.inflate(inflater, container, false)

        binding.camera.setOnClickListener {
            //"点击了照相";
            //  6.0之后动态申请权限 摄像头调取权限,SD卡写入权限
            //判断是否拥有权限，true则动态申请
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                 65
                )
                return@setOnClickListener
            }
            val intent = Intent()
            intent.action = MediaStore.ACTION_IMAGE_CAPTURE


            val fileJA: File = File(PathUtil.getPathX("fuck.jpg"))
            val uri = FileProvider.getUriForFile(requireContext(), "com.vaca.chatmygirl.fileProvider", fileJA)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
            requireActivity().startActivityForResult(intent, 96)
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
        fun viewVideo() {
           val builder_video = AlertDialog.Builder(requireContext()) //创建对话框

            val layout_video =layoutInflater.inflate(R.layout.dialog_select_video, null) //获取自定义布局
            builder_video.setView(layout_video) //设置对话框的布局
           val dialog_video = builder_video.create() //生成最终的对话框
            dialog_video.show() //显示对话框
           val takeVideoTV = layout_video.findViewById<TextView>(R.id.videoGraph)
           val chooseVideoTV = layout_video.findViewById<TextView>(R.id.video)
           val cancelVideoTV = layout_video.findViewById<TextView>(R.id.cancel_video)
            //设置监听
            takeVideoTV.setOnClickListener{
                dialog_video.dismiss()
                val intentx = Intent(MediaStore.ACTION_VIDEO_CAPTURE) // 创建一个请求视频的意图
                intentx.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1) // 设置视频的质量，值为0-1，
                intentx.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20) // 设置视频的录制长度，s为单位
                intentx.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 50 * 1024 * 1024L) // 设置视频文件大小，字节为单位
                startActivityForResult(
                    intentx,
                    93
                )

            }
            chooseVideoTV.setOnClickListener {
                dialog_video.dismiss()
                val intent: Intent = Intent(requireActivity(), VideoListActivity::class.java)
                requireActivity().startActivityForResult(intent, 86)
            }
            cancelVideoTV.setOnClickListener{
                dialog_video.dismiss()
            }
        }

        binding.video.setOnClickListener {
            viewVideo()
        }


        return binding.root
    }
}
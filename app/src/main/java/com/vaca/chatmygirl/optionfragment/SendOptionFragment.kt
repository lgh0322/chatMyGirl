package com.vaca.chatmygirl.optionfragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.scrat.app.selectorlibrary.ImageSelector
import com.vaca.chatmygirl.activity.MainActivity
import com.vaca.chatmygirl.activity.MainActivity.Companion.MAX_SELECT_COUNT
import com.vaca.chatmygirl.databinding.FragmentSendOptionBinding
import com.vaca.chatmygirl.utils.PathUtil
import java.io.File
import java.io.IOException

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
            requireActivity().startActivityForResult(intent, 1)
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
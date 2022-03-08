package com.vaca.chatmygirl.optionfragment

import android.Manifest
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
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
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import com.scrat.app.selectorlibrary.ImageSelector
import com.scrat.app.selectorlibrary.activity.ImageSelectorActivity
import com.vaca.chatmygirl.MainApplication
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.activity.VideoListActivity
import com.vaca.chatmygirl.bean.ChatBean
import com.vaca.chatmygirl.databinding.FragmentSendOptionBinding
import com.vaca.chatmygirl.utils.PathUtil
import com.zxy.tiny.Tiny
import com.zxy.tiny.Tiny.FileCompressOptions
import com.zxy.tiny.callback.FileWithBitmapCallback
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

class SendOptionFragment : Fragment() {

    lateinit var binding: FragmentSendOptionBinding

    lateinit var takePhotoLauncher: ActivityResultLauncher<Intent>
    var photoTemp=""
    var videoTemp=""

    fun takePhoto(){
        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
        photoTemp=PathUtil.getPathX("photoTemp.jpg")
        val fileJA: File = File(photoTemp)
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
            val options = FileCompressOptions()
            Tiny.getInstance().source(photoTemp).asFile().withOptions(options)
                .compress { isSuccess: Boolean, bitmap: Bitmap?, outfile: String?, t: Throwable? ->
                    Log.e("outfile",outfile!!)
                    EventBus.getDefault().post(ChatBean().apply {
                        chatType=2;
                        chatMessage=outfile
                    })
                }
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

        val requestVideoPermission= registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {

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

           it.run {
               val paths = ImageSelector.getImagePaths(data)
               if (paths == null) return@run
               if (paths.isEmpty()) return@run

               for (k in paths.indices) {
                   val options = FileCompressOptions()
                   Tiny.getInstance().source(paths!![k]).asFile().withOptions(options)
                       .compress { isSuccess: Boolean, bitmap: Bitmap?, outfile: String?, t: Throwable? ->
                           Log.e("outfile",outfile!!)
                           EventBus.getDefault().post(ChatBean().apply {
                               chatType=2;
                               chatMessage=outfile
                           })
                       }
               }

           }


        }
        binding.photo.setOnClickListener {
            val i = Intent(
                activity,
                ImageSelectorActivity::class.java
            )
            i.putExtra(ImageSelectorActivity.EXTRA_KEY_MAX, 10)
            phoneSelector.launch(i)
        }

        val openFileSelect= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.data != null) {
                val uri: Uri = it.data!!.getData() !!
                println("四点零分介绍了雕刻技法卢卡斯的Uri  ${uri.path}")
                val ff=uriToFile(MainApplication.application,uri)
                val path: String = ff!!.absolutePath
                println("四点零分介绍了雕刻技法卢卡斯的  $path")
                EventBus.getDefault().post(ChatBean().apply {
                    chatType=6;
                    chatMessage=path
                })

            }
        }

        binding.file.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
           openFileSelect.launch(intent)
        }


        val takeVideo=registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) {
            val uri = it.data!!.data!!
            val ff=uriToFile(MainApplication.application,uri)!!
//            println("发的时刻JFK撒旦解放${ff.absolutePath}")


            EventBus.getDefault().post(ChatBean().apply {
                chatType=4;
                chatMessage=ff.absolutePath
            })
        }

        val selectVideo= registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) {
            val filPaths = it.data!!.getStringExtra("path")
            EventBus.getDefault().post(ChatBean().apply {
                chatType=4;
                chatMessage=filPaths
            })
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
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    requestVideoPermission.launch( arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))

                    return@setOnClickListener
                }
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


    fun uriToFile(context: Context, uri: Uri): File? = when(uri.scheme){
        ContentResolver.SCHEME_FILE -> uri.toFile()
        ContentResolver.SCHEME_CONTENT ->{
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.let {
                if(it.moveToFirst()){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        //保存到本地
                        val ois = context.contentResolver.openInputStream(uri)
                        val displayName =
                            it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        ois?.let {
                            val file = File(
                                context.externalCacheDir!!.absolutePath,
                                "$displayName"
                            )

                            val fos = FileOutputStream(file)
                            val buffer = ByteArray(1024)
                            var len: Int = ois.read(buffer)
                            var downloaded: Long = 0
                            while (len != -1) {
                                fos.write(buffer, 0, len)
                                len = ois.read(buffer)
                            }
                            fos.close()
                            it.close()
                            file
                        }
                    }else
                    //直接转换
                        File(it.getString(it.getColumnIndex(MediaStore.Images.Media.DATA)))
                }else {
                    it.close()
                    null
                }
            }
        }
        else -> null
    }


}
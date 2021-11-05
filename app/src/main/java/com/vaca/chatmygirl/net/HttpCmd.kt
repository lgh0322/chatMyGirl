package com.vaca.chatmygirl.net

import android.util.Log
import com.vaca.chatmygirl.BuildConfig
import com.vaca.chatmygirl.utils.PathUtil
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object HttpCmd {
    val netAddress=BuildConfig.FILEURL
    private val client = OkHttpClient();
    private val JSON2: MediaType? = "multipart/form-data; charset=utf-8".toMediaTypeOrNull()

    interface OnDownloadListener {
        fun onDownloadSuccess(filePath: String?)
        fun onDownloading(progress: Int)
        fun onDownloadFailed()
    }


    fun getFile( fileName: String, listener: OnDownloadListener?) {
        val url: String= netAddress+"/download"
        val absoluteFilePath: String = PathUtil.getPathX(fileName)
        val file = File(absoluteFilePath)
        val request: Request = Request.Builder()
            .addHeader("name",fileName)
            .url(url).build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                listener?.onDownloadFailed()
            }
            override fun onResponse(call: Call, response: Response) {
                if (200 == response.code) {


                    var fileOutputStream: FileOutputStream? = null
                    var inputStream: InputStream? = null
                    try {
                        val total = response.body!!.contentLength()
                        var sum: Long = 0
                        inputStream = response.body!!.byteStream()
                        fileOutputStream = FileOutputStream(file)
                        val buffer = ByteArray(1024 * 1024)
                        var len = 0
                        while (inputStream.read(buffer).also { len = it } != -1) {
                            fileOutputStream.write(buffer, 0, len)
                            sum += len.toLong()
                            val progress = (sum * 1.0f / total * 100).toInt()
                            // 下载中
                            listener?.onDownloading(progress)
                        }
                        fileOutputStream.flush()
                        listener?.onDownloadSuccess(absoluteFilePath)
                    } catch (e: IOException) {
                        listener?.onDownloadFailed()
                    } finally {
                        inputStream?.close()
                        fileOutputStream?.close()
                    }
                } else {
                    listener?.onDownloadFailed()
                }
            }

        }


        )
    }







    @Throws(IOException::class)
    fun uploadFile(file: File, pro: UploadProgressListener): String? {
        val url = netAddress + "/upload"

        val builder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(
                "uploadFile", file.name,
                file.asRequestBody(JSON2)
            )


        val exMultipartBody =
            ExMultipartBody(builder.build(), pro)

        val request: Request = Request.Builder()
            .addHeader("Content-Type", "application/json; charset=UTF-8")
            .url(url).post(exMultipartBody)
            .build()
        client.newCall(request)
            .execute()
            .use { response ->
                response.body?.string()?.let { Log.e("sdf", it);return it }
            }
        return null
    }

}
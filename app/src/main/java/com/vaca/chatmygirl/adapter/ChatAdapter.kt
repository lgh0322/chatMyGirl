package com.vaca.chatmygirl.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.activity.MainActivity
import com.vaca.chatmygirl.bean.ChatBean
import com.vaca.chatmygirl.gif.AnimatedGifDrawable
import com.vaca.chatmygirl.gif.AnimatedGifDrawable.UpdateListener
import com.vaca.chatmygirl.gif.AnimatedImageSpan
import com.vaca.chatmygirl.holder.*
import com.vaca.chatmygirl.utils.PathUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.regex.Pattern

class ChatAdapter(private val mContext: Context, private val rv: RecyclerView) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val chatList: MutableList<ChatBean> = ArrayList()
    var itemClickListener: OnItemClickListener? = null
    fun setList(chatList: List<ChatBean>?) {
        this.chatList.addAll(0, (chatList)!!)
        notifyDataSetChanged()
    }

    fun setData(bean: ChatBean) {
        chatList.add(bean)
        notifyItemChanged(chatList.size-1)
    }

    override fun getItemViewType(position: Int): Int {
        return chatList[position].chatType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            0 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_send_text, parent, false)
                viewHolder = ChatTextSendHolder(view)
            }
            1 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_receive_text, parent, false)
                viewHolder = ChatTextReceiveHolder(view)
            }
            2 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_send_image, parent, false)
                viewHolder = ChatImageSendHolder(view)
            }
            3 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_receive_text, parent, false)
                viewHolder = ChatTextReceiveHolder(view)
            }
            4 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_send_video, parent, false)
                viewHolder = ChatVideoSendHolder(view)
            }
            5 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_receive_text, parent, false)
                viewHolder = ChatTextReceiveHolder(view)
            }
            6 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_send_file, parent, false)
                viewHolder = ChatFileSendHolder(view)
            }
            7 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_receive_text, parent, false)
                viewHolder = ChatTextReceiveHolder(view)
            }
            8 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_send_text, parent, false)
                viewHolder = ChatTextSendHolder(view)
            }
            9 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_chat_receive_text, parent, false)
                viewHolder = ChatTextReceiveHolder(view)
            }
        }
        return viewHolder!!
    }

    fun bitMapScale(bitmap: Bitmap, scale: Float): Bitmap {
        val matrix = Matrix()
        matrix.postScale(scale, scale) //长和宽放大缩小的比例
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.getWidth(),
            bitmap.getHeight(),
            matrix,
            true
        )
    }

    private fun handler(gifTextView: TextView, content: String): SpannableStringBuilder {
        val sb = SpannableStringBuilder(content)
        val regex = "(\\[f_static_)\\d{1,3}(\\])"
        val p = Pattern.compile(regex)
        val m = p.matcher(content)
        while (m.find()) {
            val tempText = m.group()
            try {
                val num = tempText.substring("[f_static_".length, tempText.length - "]".length)
                val gif = "face/gif/f$num.gif"
                Log.i("嘎嘎嘎", gif)
                /**
                 * 如果open这里不抛异常说明存在gif，则显示对应的gif
                 * 否则说明gif找不到，则显示png
                 */
                val `is` = mContext.assets.open(gif)
                sb.setSpan(
                    AnimatedImageSpan(
                        AnimatedGifDrawable(
                            `is`,
                            UpdateListener { gifTextView.postInvalidate() })
                    ), m.start(), m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                `is`.close()
            } catch (e: Exception) {
                Log.i("amp", "haha")
                val png = tempText.substring("[".length, tempText.length - "]".length)
                try {
                    sb.setSpan(
                        ImageSpan(
                            mContext,
                                BitmapFactory.decodeStream(
                                    mContext.assets.open(
                                        "face/png/$png.png"
                                    )
                                )
                        ), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } catch (e1: IOException) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace()
                }
                e.printStackTrace()
            }
        }
        return sb
    }


    fun md5(data: String): String? {
        val md: MessageDigest = MessageDigest.getInstance("MD5")
        md.update(data.toByteArray())
        val buf = StringBuffer()
        val bits: ByteArray = md.digest()
        for (i in bits.indices) {
            var a = bits[i].toInt()
            if (a < 0) a += 256
            if (a < 16) buf.append("0")
            buf.append(Integer.toHexString(a))
        }
        return buf.toString().substring(0,16)
    }

    fun getVideoThumbnail(videoPath: String?, width: Int, height: Int, kind: Int): Bitmap {
        var bitmap: Bitmap? = null
        bitmap = ThumbnailUtils.createVideoThumbnail(
            videoPath!!,
            kind
        )
        if (bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(
                bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT
            )
        }
        return bitmap!!
    }

    private fun showContent(holder: RecyclerView.ViewHolder?, bean: ChatBean) {
        when (holder!!.itemViewType) {
            0 -> {
                val sb = handler(
                    (holder as ChatTextSendHolder?)!!.tv_content,
                    bean.chatMessage
                )
                (holder as ChatTextSendHolder?)!!.tv_content.text = sb
            }
            1 -> {
                val sb = handler(
                    (holder as ChatTextReceiveHolder?)!!.tv_content,
                    bean.chatMessage
                )
                (holder as ChatTextReceiveHolder?)!!.tv_content.text = sb
            }
            2->{

                Glide.with(mContext).load(bean.chatMessage).into((holder as ChatImageSendHolder?)!!.img_content)
            }



            4->{
                val dd: File =File(PathUtil.getPathX( md5(bean.chatMessage)+".jpg"))
                if(dd.exists()){
                    dd.delete()
                }
                val gaga: Bitmap =getVideoThumbnail(
                    bean.chatMessage,
                    200,
                    300,
                    MediaStore.Video.Thumbnails.MINI_KIND
                )
                val fout=FileOutputStream(dd)
               gaga.compress(Bitmap.CompressFormat.JPEG, 80,fout)
                fout.flush()
                fout.close()

                Glide.with(mContext).load(dd.absolutePath).into((holder as ChatVideoSendHolder?)!!.img_content)
            }

            6->{
                val nn=bean.chatMessage.lastIndexOf("/")
                val strx=bean.chatMessage.substring(nn+1,bean.chatMessage.length)
                (holder as ChatFileSendHolder?)!!.fileName.text=strx

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatBean = chatList[position]
        showContent(holder, chatBean)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    interface OnItemClickListener {
        fun onItemClick(a: Int)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener?) {
        this.itemClickListener = itemClickListener
    }


}
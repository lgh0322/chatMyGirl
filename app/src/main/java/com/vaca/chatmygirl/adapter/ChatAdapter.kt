package com.vaca.chatmygirl.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.bean.ChatBean
import com.vaca.chatmygirl.gif.AnimatedGifDrawable
import com.vaca.chatmygirl.gif.AnimatedGifDrawable.UpdateListener
import com.vaca.chatmygirl.gif.AnimatedImageSpan
import com.vaca.chatmygirl.holder.ChatTextReceiveHolder
import com.vaca.chatmygirl.holder.ChatTextSendHolder
import java.io.IOException
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
        notifyDataSetChanged()
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
                            mContext, bitMapScale(
                                BitmapFactory.decodeStream(
                                    mContext.assets.open(
                                        "face/png/$png.png"
                                    )
                                ), 2.5f
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
                val sb2 = handler(
                    (holder as ChatTextReceiveHolder?)!!.tv_content,
                    bean.chatMessage
                )
                (holder as ChatTextReceiveHolder?)!!.tv_content.text = sb2
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
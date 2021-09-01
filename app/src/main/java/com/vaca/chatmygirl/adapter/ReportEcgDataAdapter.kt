package com.vaca.chatmygirl.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.interfaces.SwipeAdapterInterface
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface
import com.daimajia.swipe.util.Attributes
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.bean.ChatListBean
import com.vaca.chatmygirl.ble.BleServer
import com.vaca.chatmygirl.view.CustomSwipeLayout

import java.util.*

class ReportEcgDataAdapter(
    val context: Context,
) :
    RecyclerView.Adapter<ReportEcgDataAdapter.ViewHolder>(), SwipeItemMangerInterface,
    SwipeAdapterInterface {


    var mEcgData: MutableList<ChatListBean> = ArrayList()


    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    var currentSelect: Int = 0


    interface ClickEcg {
        fun givePosition(position: Int, date: String)
        fun clear()
    }

    var clickEcg: ClickEcg? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_chat_list, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



    }





    fun setList(userBean: List<ChatListBean>) {
        mEcgData.clear()
        mEcgData = userBean as MutableList<ChatListBean>
        notifyDataSetChanged()
    }

    private fun timeConvert(s: String): String {
        var t: String = s.substring(1, 5)
        t += "-"
        t += s.substring(5, 7)
        t += "-"
        t += s.substring(7, 9)
        t += " "
        t += s.substring(9, 11)
        t += ":"
        t += s.substring(11, 13)
        t += ":"
        t += s.subSequence(13, 15)
        return t
    }


    override fun getItemCount(): Int {
        return mEcgData.size
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val timeStart: TextView = itemView.findViewById(R.id.timeStart)
        val moT: TextView = itemView.findViewById(R.id.monitorTime)
        val x1: ImageView = itemView.findViewById(R.id.x1)
        val result: TextView = itemView.findViewById(R.id.result)
        val del = itemView.findViewById<TextView>(R.id.be_bp_list_delete)

        val mainItem: LinearLayout = itemView.findViewById(R.id.gaga)
        val mainView: CustomSwipeLayout = itemView.findViewById(R.id.bp_swipe)

        init {

            del.setOnClickListener {

            }

            var multiClick = false
            mainItem.setOnClickListener {

            }


        }
    }

    override fun openItem(position: Int) {

    }

    override fun closeItem(position: Int) {
        TODO("Not yet implemented")
    }

    override fun closeAllExcept(layout: SwipeLayout?) {
        TODO("Not yet implemented")
    }

    override fun closeAllItems() {
        TODO("Not yet implemented")
    }

    override fun getOpenItems(): MutableList<Int> {
        TODO("Not yet implemented")
    }

    override fun getOpenLayouts(): MutableList<SwipeLayout> {
        TODO("Not yet implemented")
    }

    override fun removeShownLayouts(layout: SwipeLayout?) {
        TODO("Not yet implemented")
    }

    override fun isOpen(position: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getMode(): Attributes.Mode {
        TODO("Not yet implemented")
    }

    override fun setMode(mode: Attributes.Mode?) {
        TODO("Not yet implemented")
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        TODO("Not yet implemented")
    }


}
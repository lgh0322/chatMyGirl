package com.vaca.chatmygirl.adapter

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.bean.BookBean
import org.json.JSONArray

import java.util.*

class BookViewAdapter(context: Context) : RecyclerView.Adapter<BookViewAdapter.ViewHolder>() {
    private val mBleData: MutableList<BookBean>
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null
    private val mContext: Context

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_ble, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var nn=mBleData[position].name
        nn=nn.replace(".txt","",ignoreCase = true)

        holder.bleName.text = nn
    }

    fun addAllDevice(name: JSONArray) {
        mBleData.clear()
        for(k in 0 until name.length()){
            mBleData.add(BookBean( name[k].toString()))
        }
        notifyDataSetChanged()
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mBleData.size
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onScanItemClick(name:String)
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var bleName: TextView = itemView.findViewById(R.id.ble_name)
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onScanItemClick(mBleData[adapterPosition].name)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    // data is passed into the constructor
    init {
        mBleData = ArrayList()
        mContext = context
    }
}
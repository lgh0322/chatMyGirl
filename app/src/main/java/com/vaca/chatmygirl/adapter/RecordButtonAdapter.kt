package com.vaca.chatmygirl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.bean.HomeMember


class RecordButtonAdapter(var context: Context) :
    RecyclerView.Adapter<RecordButtonAdapter.ViewHolder>() {
    interface WantInfo {
        fun go(u: Int)
    }

    var myGo: WantInfo? = null


    private val mInflater: LayoutInflater = LayoutInflater.from(context)


    val mem = ArrayList<HomeMember>()

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_record_button, parent, false)
        return ViewHolder(view)
    }


    // binds the data to the TextView in each cell
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mem[position].select) {
            holder.text.background = ContextCompat.getDrawable(context, R.drawable.top_button_bg)
            holder.text.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.text.background = null
            holder.text.setTextColor(ContextCompat.getColor(context, R.color.login_hint_black))
        }

        holder.text.text = mem[position].name

    }


    fun setSelect(j: Int) {
        for (k in mem.indices) {
            mem[k].select = k == j
        }
        notifyDataSetChanged()
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mem.size
    }


    fun addAll(userBean: Array<String>) {
        mem.clear()
        for (k in userBean.indices) {
            mem.add(HomeMember(userBean[k], false))
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val text = itemView.findViewById<TextView>(R.id.name)

        override fun onClick(view: View) {
            myGo?.go(layoutPosition)
            for (k in 0 until mem.size) {
                mem[k].select = false
            }
            mem[layoutPosition].select = true
            notifyDataSetChanged()

        }

        init {
            itemView.setOnClickListener(this)
        }
    }


}
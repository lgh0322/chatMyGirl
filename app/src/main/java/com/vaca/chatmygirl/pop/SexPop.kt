package com.vaca.chatmygirl.pop

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.super_rabbit.wheel_picker.WheelAdapter
import com.super_rabbit.wheel_picker.WheelPicker
import com.vaca.chatmygirl.R

@SuppressLint("ClickableViewAccessibility")
class SexPop(mContext: Context, r: ReceiveInfo) : PopupWindow() {
    private val mContext: Context? = null

    interface ReceiveInfo {
        fun receive(s: String)
    }


    init {
        val view = LayoutInflater.from(mContext).inflate(R.layout.pop_sex, null)
        isOutsideTouchable = true
        contentView = view
        height = RelativeLayout.LayoutParams.MATCH_PARENT
        width = RelativeLayout.LayoutParams.MATCH_PARENT
        isFocusable = true
        val dw = ColorDrawable(-0x80000000)
        setBackgroundDrawable(dw)
        this.animationStyle = R.style.take_photo_anim


        val sexPick: WheelPicker = view.findViewById(R.id.sexPick)

        sexPick.apply {
            setWheelItemCount(3)
            setAdapter(SexPickerAdapter())
            setUnselectedTextColor(R.color.get_pin_gray)
        }

        val close: ImageView = view.findViewById(R.id.close)
        close.setOnClickListener {
            r.receive(sexPick.getCurrentItem())
            dismiss()
        }

    }


    inner class SexPickerAdapter : WheelAdapter {
        //get item value based on item position in wheel
        override fun getValue(position: Int): String {
            if (position == -1)
                return "男"
            if (position == 0)
                return "女"
            return ""
        }

        //get item position based on item string value
        override fun getPosition(vale: String): Int {
            return 0
        }

        //return a string with the approximate longest text width, for supporting WRAP_CONTENT
        override fun getTextWithMaximumLength(): String {
            return "Mmm 00, 0000"
        }

        //return the maximum index
        override fun getMaxIndex(): Int {
            return 0
        }

        //return the minimum index
        override fun getMinIndex(): Int {
            return -1
        }
    }
}
package com.vaca.chatmygirl.pop

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.super_rabbit.wheel_picker.WheelPicker
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.popadapter.UserAgeAdapter

@SuppressLint("ClickableViewAccessibility")
class AgePop(mContext: Context, r: ReceiveInfo, initValue: String?) : PopupWindow() {
    private val mContext: Context? = null

    interface ReceiveInfo {
        fun receive(s: String)
    }


    init {
        val view = LayoutInflater.from(mContext).inflate(R.layout.pop_age, null)
        isOutsideTouchable = true
        contentView = view
        height = RelativeLayout.LayoutParams.MATCH_PARENT
        width = RelativeLayout.LayoutParams.MATCH_PARENT
        isFocusable = true
        val dw = ColorDrawable(-0x80000000)
        setBackgroundDrawable(dw)
        this.animationStyle = R.style.take_photo_anim

        var heightInit = 35
        if (!initValue.isNullOrEmpty()) {
            val len = initValue.length - 3
            try {
                heightInit = initValue.substring(0, len).toInt()
            } catch (e: Exception) {

            }

        }

        val sexPick: WheelPicker = view.findViewById(R.id.sexPick)

        sexPick.apply {
            setWheelItemCount(5)
            setAdapter(UserAgeAdapter(heightInit))
            setUnselectedTextColor(R.color.get_pin_gray)
        }

        val close: ImageView = view.findViewById(R.id.close)
        close.setOnClickListener {
            r.receive(sexPick.getCurrentItem())
            dismiss()
        }

    }


}
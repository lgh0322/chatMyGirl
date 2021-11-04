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
import com.vaca.chatmygirl.popadapter.UserWeightAdapter

@SuppressLint("ClickableViewAccessibility")
class WeightPop(mContext: Context, r: recieveInfo, initValue: String?) : PopupWindow() {
    private val mContext: Context? = null

    interface recieveInfo {
        fun recieve(s: String)
    }


    init {
        val view = LayoutInflater.from(mContext).inflate(R.layout.pop_weight, null)
        isOutsideTouchable = true
        contentView = view
        height = RelativeLayout.LayoutParams.MATCH_PARENT
        width = RelativeLayout.LayoutParams.MATCH_PARENT
        isFocusable = true
        val dw = ColorDrawable(-0x80000000)
        setBackgroundDrawable(dw)
        this.animationStyle = R.style.take_photo_anim

        var weightInit = 100

        if (!initValue.isNullOrEmpty()) {
            val len = initValue.length - 3
            try {
                val weightF = initValue.substring(0, len).toFloat()
                weightInit = (weightF * 2).toInt()
            } catch (e: Exception) {

            }

        }


        val sexPick: WheelPicker = view.findViewById(R.id.sexPick)

        sexPick.apply {
            setWheelItemCount(5)
            setAdapter(UserWeightAdapter(weightInit))
            setUnselectedTextColor(R.color.get_pin_gray)
        }

        val close: ImageView = view.findViewById(R.id.close)
        close.setOnClickListener {
            r.recieve(sexPick.getCurrentItem())
            dismiss()
        }

    }


}
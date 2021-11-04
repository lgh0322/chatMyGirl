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
import com.vaca.chatmygirl.popadapter.UserDateAdapter
import com.vaca.chatmygirl.popadapter.UserMonthAdapter
import com.vaca.chatmygirl.popadapter.UserYearAdapter

@SuppressLint("ClickableViewAccessibility")
class DatePop(mContext: Context, r: ReceiveInfo, initValue: String?) : PopupWindow() {
    private val mContext: Context? = null

    interface ReceiveInfo {
        fun receive(s: String)
    }


    init {
        val view = LayoutInflater.from(mContext).inflate(R.layout.pop_date, null)
        isOutsideTouchable = true
        contentView = view
        height = RelativeLayout.LayoutParams.MATCH_PARENT
        width = RelativeLayout.LayoutParams.MATCH_PARENT
        isFocusable = true
        val dw = ColorDrawable(-0x80000000)
        setBackgroundDrawable(dw)
        this.animationStyle = R.style.take_photo_anim

        val yearPick: WheelPicker = view.findViewById(R.id.x1Pick)
        val monthPick: WheelPicker = view.findViewById(R.id.x2Pick)
        val dayPick: WheelPicker = view.findViewById(R.id.x3Pick)


        var yearInit = 2000
        var monthInit = 5
        var dateInit = 15

        if (!initValue.isNullOrEmpty()) {
            try {
                yearInit = initValue.substring(0, 4).toInt()
                monthInit = initValue.substring(5, 7).toInt() - 1
                dateInit = initValue.substring(8, 10).toInt()
            } catch (e: Exception) {

            }

        }



        yearPick.apply {
            setWheelItemCount(5)
            setAdapter(UserYearAdapter(yearInit))
        }

        monthPick.apply {
            setWheelItemCount(5)
            setAdapter(UserMonthAdapter(monthInit))
        }

        dayPick.apply {
            setWheelItemCount(5)
            setAdapter(UserDateAdapter(dateInit))
        }


        val close: ImageView = view.findViewById(R.id.close)
        close.setOnClickListener {
            r.receive("${yearPick.getCurrentItem()}-${monthPick.getCurrentItem()}-${dayPick.getCurrentItem()}")
            dismiss()
        }

    }


}
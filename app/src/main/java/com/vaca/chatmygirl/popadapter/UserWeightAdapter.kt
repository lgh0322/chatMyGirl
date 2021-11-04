package com.vaca.chatmygirl.popadapter

import com.super_rabbit.wheel_picker.WheelAdapter


class UserWeightAdapter(var init: Int) : WheelAdapter {
    private var initValue: Int = 0;


    override fun getValue(position: Int): String {
        var x = position + init
        while (x > 499) {
            x -= 500;
        }
        while (x < 0) {
            x += 500;
        }
        return String.format("%.1f", x.toFloat() / 2f)
    }


    fun setInitValue(value: Int) {
        initValue = value;
    }

    //return the maximum index
    override fun getMaxIndex(): Int {
        return Int.MAX_VALUE
    }

    //return the minimum index
    override fun getMinIndex(): Int {
        return Int.MIN_VALUE
    }

    override fun getPosition(vale: String): Int {
        return 5
    }

    override fun getTextWithMaximumLength(): String {
        return "2000.0"
    }
}
package com.vaca.chatmygirl.popadapter

import com.super_rabbit.wheel_picker.WheelAdapter


class UserMonthAdapter(var init: Int) : WheelAdapter {
    private var initValue: Int = 0;


    override fun getValue(position: Int): String {
        var x = position + init
        while (x > 11) {
            x -= 12;
        }
        while (x < 0) {
            x += 12;
        }
        return String.format("%02d", x + 1)
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
        return 4
    }

    override fun getTextWithMaximumLength(): String {
        return "2020"
    }
}
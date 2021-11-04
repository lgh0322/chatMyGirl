package com.vaca.chatmygirl.popadapter

import com.super_rabbit.wheel_picker.WheelAdapter


class UserYearAdapter(var init: Int) : WheelAdapter {
    private var initValue: Int = 0;
    override fun getValue(position: Int): String {
        var x = position + init
        return x.toString()
    }


    fun setInitValue(value: Int) {
        initValue = value;
    }

    //return the maximum index
    override fun getMaxIndex(): Int {
        return Integer.MAX_VALUE
    }

    //return the minimum index
    override fun getMinIndex(): Int {
        return Integer.MIN_VALUE
    }

    override fun getPosition(vale: String): Int {
        return 4
    }

    override fun getTextWithMaximumLength(): String {
        return "2020"
    }
}
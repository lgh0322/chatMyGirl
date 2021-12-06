package com.vaca.chatmygirl.bean

import com.vaca.chatmygirl.utils.HanyuParser
import java.util.*

class SortModel(var name: String = "", var id: String = "") {


    var sortLetters: String? = null
    var isSelectState: Boolean = false

    var status: String? = null


    init {

        val pinyin = HanyuParser().getStringPinYin(name)
        val sortString = pinyin.substring(0, 1).toUpperCase(Locale.getDefault())
        if (sortString.matches(Regex("[A-Z]"))) {
            sortLetters = sortString.toUpperCase(Locale.getDefault())
        } else {
            sortLetters = "#"
        }
    }


}


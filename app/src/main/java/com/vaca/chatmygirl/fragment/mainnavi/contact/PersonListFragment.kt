package com.vaca.chatmygirl.fragment.mainnavi.contact

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.adapter.SortAdapter
import com.vaca.chatmygirl.bean.SortModel
import com.vaca.chatmygirl.databinding.FragmentLoginBinding
import com.vaca.chatmygirl.databinding.FragmentMainBinding
import com.vaca.chatmygirl.databinding.FragmentPersonListBinding
import com.vaca.chatmygirl.stickylistheaders.StickyListHeadersListView
import com.vaca.chatmygirl.utils.HanyuParser
import com.vaca.chatmygirl.utils.PinyinComparator
import java.util.*

class PersonListFragment: Fragment() {

    lateinit var binding:FragmentPersonListBinding
    private var adapter: SortAdapter? = null
    private var SourceDateList: MutableList<SortModel>? = null
    private var SourceDateList2: MutableList<SortModel>? = null
    private val pinyinComparator=PinyinComparator()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{


        binding= FragmentPersonListBinding.inflate(inflater,container,false)
        val stickyList=binding.list
        binding.bar.setTextView(binding.dialog)
        binding.bar.setOnTouchingLetterChangedListener {

            val yes=adapter
            if(yes!=null){
                val position = yes.getPositionForSection(it[0].code)
                if (position != -1) {
                    stickyList.setSelection(position)
                }
            }

        }




        stickyList.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

        })
        stickyList.setOnStickyHeaderChangedListener(object :StickyListHeadersListView.OnStickyHeaderChangedListener{
            override fun onStickyHeaderChanged(
                l: StickyListHeadersListView?,
                header: View?,
                itemPosition: Int,
                headerId: Long
            ) {
                header!!.alpha = 1f

            }

        })
        stickyList.setOnStickyHeaderOffsetChangedListener(object :StickyListHeadersListView.OnStickyHeaderOffsetChangedListener{
            override fun onStickyHeaderOffsetChanged(
                l: StickyListHeadersListView?,
                header: View?,
                offset: Int
            ) {
                header!!.alpha = 1 - offset / header.measuredHeight.toFloat()
            }

        })

        stickyList.isDrawingListUnderStickyHeader = true
        stickyList.areHeadersSticky = true
        stickyList.stickyHeaderTopOffset = -10
        stickyList.setFastScrollEnabled(false)
        stickyList.isFastScrollAlwaysVisible = false


        val gugu=SortModel("吃屎","234")



        SourceDateList= arrayListOf(gugu)
        Collections.sort(SourceDateList, pinyinComparator)
        SourceDateList2 = SourceDateList
        adapter = SortAdapter(requireContext(), SourceDateList, false)
        stickyList.adapter = adapter

        return binding.root
    }


    private fun filledData(date: Array<String>?, id: Array<String>?): List<SortModel>? {
        val mSortList: MutableList<SortModel> = ArrayList<SortModel>()
        if (null == date) {
            return ArrayList<SortModel>()
        } else {
            if (date.size == 0) {
                return ArrayList<SortModel>()
            }
        }
        if (null == id) {
            return ArrayList<SortModel>()
        } else {
            if (id.size == 0) {
                return ArrayList<SortModel>()
            }
        }
        for (i in date.indices) {
            val sortModel = SortModel()
            sortModel.name = date[i]
            sortModel.id = id[i]
            // 汉字转换成拼音
            val pinyin: String = HanyuParser().getStringPinYin(date[i])
            val sortString = pinyin.substring(0, 1).toUpperCase(Locale.getDefault())
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches(Regex ("[A-Z]"))) {
                sortModel.sortLetters = sortString.toUpperCase(Locale.getDefault())
            } else {
                sortModel.sortLetters = "#"
            }
            mSortList.add(sortModel)
        }
        return mSortList
    }

    private fun filledData_Person(date: Array<String>?, id: Array<String>?): List<SortModel>? {
        val mSortList: MutableList<SortModel> = ArrayList<SortModel>()
        if (null == date) {
            return ArrayList<SortModel>()
        } else {
            if (date.size == 0) {
                return ArrayList<SortModel>()
            }
        }
        if (null == id) {
            return ArrayList<SortModel>()
        } else {
            if (id.size == 0) {
                return ArrayList<SortModel>()
            }
        }
        for (i in date.indices) {
            val sortModel = SortModel()
            sortModel.name = date[i]
            sortModel.id = id[i]

            // 汉字转换成拼音
            val pinyin: String = HanyuParser().getStringPinYin(date[i])
            val sortString = pinyin.substring(0, 1).toUpperCase(Locale.getDefault())
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches(Regex("[A-Z]"))) {
                sortModel.sortLetters = sortString.toUpperCase(Locale.getDefault())
            } else {
                sortModel.sortLetters = "#"
            }
            mSortList.add(sortModel)
        }
        return mSortList
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private fun filterData(filterStr: String) {

            var filterDateList: MutableList<SortModel> = ArrayList<SortModel>()
            if (TextUtils.isEmpty(filterStr)) {
                if (SourceDateList != null) {
                    filterDateList = SourceDateList as MutableList<SortModel>
                }
            } else {
                filterDateList.clear()
                if (SourceDateList != null) {
                    for (sortModel in SourceDateList!!!!) {
                        val name: String = sortModel.name!!
                        if (containString(name, filterStr)) {
                            filterDateList.add(sortModel)
                        }
                    }
                }
            }
            if (filterDateList == null) filterDateList = ArrayList<SortModel>()

            // 根据a-z进行排序
            Collections.sort(filterDateList, pinyinComparator)
            SourceDateList2 = filterDateList
            if (adapter == null) return
            if (filterDateList == null) return
            adapter!!.updateListView(filterDateList)

    }

    private fun containString(name: String, filterStr: String): Boolean {
        var namePinyin: String = HanyuParser().getStringPinYin(name)
        for (i in 0 until filterStr.length) {
            val singleStr = filterStr.substring(i, i + 1)
            //汉字
            if (name.contains(singleStr)) {
                if (i == filterStr.length - 1) {
                    return true
                }
                continue
            }
            //英文
            namePinyin = if (namePinyin.contains(singleStr)) {
                val currentIndex = namePinyin.indexOf(singleStr)
                namePinyin.substring(currentIndex + 1, namePinyin.length)
            } else { //不包含
                break
            }
            if (i == filterStr.length - 1) {
                return true
            }
        }
        return false
    }
}
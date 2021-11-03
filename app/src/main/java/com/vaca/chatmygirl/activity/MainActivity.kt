package com.vaca.chatmygirl.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.jeremyliao.liveeventbus.LiveEventBus
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.event.GoGo
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    /**
     * 点击空白区域隐藏键盘.
     */
    override fun dispatchTouchEvent(me: MotionEvent): Boolean {
        if (me.action == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            val v = currentFocus //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v!!.windowToken) //收起键盘
            }
        }
        return super.dispatchTouchEvent(me)
    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {  //判断得到的焦点控件是否包含EditText
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            //得到输入框在屏幕中上下左右的位置
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return if (event.x > left && event.x < right && event.y > top && event.y < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                false
            } else {
                true
            }
        }
        // 如果焦点不是EditText则忽略
        return false
    }


    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private fun hideKeyboard(token: IBinder?) {
        if (token != null) {
            val im: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainScope().launch {
            delay(5000)
            GoGo.goChat()
        }

        LiveEventBus.get("rootGo",String::class.java).observeForever(Observer {
            goPlace->
            val navController = findNavController(R.id.nav_host_fragment)
            when(goPlace){
                "chat"->{
                    if(!navController.popBackStack(R.id.chatFragment,false)){
                        navController.navigate(R.id.chatFragment)
                    }
                }
            }
        })




    }
}
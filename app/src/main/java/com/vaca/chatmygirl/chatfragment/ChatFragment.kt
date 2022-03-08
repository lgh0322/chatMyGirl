package com.vaca.chatmygirl.chatfragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.adapter.ChatAdapter
import com.vaca.chatmygirl.bean.ChatBean
import com.vaca.chatmygirl.data.MyStorage
import com.vaca.chatmygirl.databinding.FragmentChatBinding
import com.vaca.chatmygirl.event.Emotion
import com.vaca.chatmygirl.keyboard.KeyboardHeightProvider
import com.vaca.chatmygirl.net.NetCmd
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import java.util.regex.Pattern

//--------------------------------这个是聊天界面
class ChatFragment : Fragment() {

    var scrollX = 0f
    var scrollY = 0f
    lateinit var keyboardHeightProvider: KeyboardHeightProvider
    lateinit var binding: FragmentChatBinding
    var chatMsg = ArrayList<ChatBean>()
    var keyboardVisible = false
    lateinit var chatAdapter: ChatAdapter
    private fun showKeyboard(view: View) {
        val imm = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.requestFocus()
        imm.showSoftInput(view, 0)
    }

    var hiddAll = false

    private val topId = arrayOf(
        R.id.sendOptionFragment,
        R.id.faceFragment,
        R.id.voiceFragment,
    )
    lateinit var navController: NavController
    var currentIndex = 0
    lateinit var graph: NavGraph

    var maxK = 0;
    var miniK = 0;
    var mmK = 0


    override fun onPause() {
        super.onPause()
        keyboardHeightProvider.setKeyboardHeightObserver(null)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        keyboardHeightProvider = KeyboardHeightProvider(requireActivity())

        binding = FragmentChatBinding.inflate(inflater, container, false)

        binding.root.post { keyboardHeightProvider.start() }

        chatAdapter = ChatAdapter(requireContext(), binding.rc)
        chatAdapter.click=object :ChatAdapter.Click{
            override fun showDetail(type: String, content: String) {
                Log.e("type",type+content)
            }

        }







        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.rc.setLayoutManager(linearLayoutManager)
        binding.rc.setAdapter(chatAdapter)

        val fm = childFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = fm.navController
        graph = navController.navInflater.inflate(R.navigation.send_navigation)


        val editText = binding.constraintLayout

        val lp = binding.container.layoutParams
        lp.height = MyStorage.keyboardHeightX
        binding.container.layoutParams = lp

//------------------------------------------------------------------为了获取键盘高度做了很多工作。
        keyboardHeightProvider.setKeyboardHeightObserver { height, orientation ->
            var isVisible = (height > 0)
            val softKeybardHeight = height
            Log.e("fuckyou", height.toString())
            if (isVisible) {
                if (maxK > 0) {
                    if (height > maxK) {
                        return@setKeyboardHeightObserver
                    }
                }
                maxK = height
                mmK = maxK - miniK
                val lp = binding.container.layoutParams
                MyStorage.setKeyboardHeight(mmK)
                lp.height = mmK
                binding.container.layoutParams = lp
                if (binding.container.visibility == View.VISIBLE) {
                    val newLayoutParams = editText.layoutParams as ConstraintLayout.LayoutParams
                    newLayoutParams.bottomMargin = 0
                    editText.layoutParams = newLayoutParams
                } else {

                    val newLayoutParams = editText.layoutParams as ConstraintLayout.LayoutParams
                    newLayoutParams.bottomMargin = mmK
                    editText.layoutParams = newLayoutParams


                }

            } else {
                miniK = height
//                if(hiddAll){
//                    hiddAll=false
                /*   val newLayoutParams = editText.layoutParams as ConstraintLayout.LayoutParams
                   newLayoutParams.bottomMargin=0
                   editText.layoutParams = newLayoutParams*/
//                }
            }
            keyboardVisible = isVisible

        }

//-----------------------------------------------------这里是点击空白处隐藏键盘。
        binding.rc.setOnTouchListener { v, event ->
            if (event.getAction() === MotionEvent.ACTION_DOWN) {
                scrollX = event.getX()
                scrollY = event.getY()
            }
            if (event.getAction() === MotionEvent.ACTION_UP) {
                if (v.getId() !== 0 && Math.abs(scrollX - event.getX()) <= 5 && Math.abs(
                        scrollY - event.getY()
                    ) <= 5
                ) {
                    MainScope().launch {
//                        hiddAll=true
                        binding.container.visibility = View.GONE
                        delay(100)
                        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                            requireActivity().getCurrentFocus()?.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS
                        );

                        delay(100)
                        val newLayoutParams = editText.layoutParams as ConstraintLayout.LayoutParams
                        newLayoutParams.bottomMargin = 0
                        editText.layoutParams = newLayoutParams


                    }
                }
            }
            false
        }



        //-------------------------------------------这个是发送按钮。

        binding.messageSend.setOnClickListener {
            val text = binding.chatMessage.text.toString()
            if (text.isNotEmpty()) {
                val da = ChatBean()
                da.setChatMessage(text)
                da.setChatType(0)
                chatMsg.add(da)
                chatAdapter.setData(da)
                NetCmd.chatText(text, false)
                binding.rc.smoothScrollToPosition(chatMsg.size - 1)
                binding.chatMessage.setText("")
            }
        }




        //------------------------------------------这个是右边的加号， 可以弹出发送的各种选项。
        binding.plus.setOnClickListener {
            jump(0)
        }
        //------------------------------------------这个是右边的加号， 发送语音的。
        binding.voice.setOnClickListener {
            jump(2)
        }


        //----------------------------------------这个是发送表情的。
        binding.emotion.setOnClickListener {
            jump(1)
        }


        //----------------------------------------------------这个是监听输入文字的变化的。
        binding.chatMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val text = binding.chatMessage.text.toString()
                if (text.isEmpty()) {
                    binding.messageSend.visibility = View.GONE
                } else {
                    binding.messageSend.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        return binding.root
    }

//-----------------------------------------这里是下方的选择弹窗。 还有键盘相关的。
    private fun jump(x: Int) {
        val newLayoutParams = binding.constraintLayout.layoutParams as ConstraintLayout.LayoutParams
        newLayoutParams.bottomMargin = 0
        binding.constraintLayout.layoutParams = newLayoutParams
        graph.startDestination = topId[x]
        navController.graph = graph
        if (currentIndex != x) {
            currentIndex = x
            MainScope().launch {
                graph.startDestination = topId[x]
                navController.graph = graph
                delay(100)
                if (!navController.popBackStack(topId[x], false)) {
                    navController.navigate(topId[x])
                }
                if (keyboardVisible) {
                    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        requireActivity().getCurrentFocus()?.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS
                    );
                }
            }

        } else {
            if (keyboardVisible) {

                (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    requireActivity().getCurrentFocus()?.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
                );
                MainScope().launch {
                    delay(200)
                    binding.container.visibility = View.VISIBLE
                }
            } else {
                if (binding.container.visibility == View.VISIBLE) {
                    showKeyboard(binding.chatMessage)
                } else {
                    binding.container.visibility = View.VISIBLE
                }

            }
        }
    }

    private fun isDeletePng(cursor: Int): Boolean {
        val st = "[f_static_000]"
        val content: String = binding.chatMessage.text.toString().substring(0, cursor)
        if (content.length >= st.length) {
            val checkStr = content.substring(
                content.length - st.length,
                content.length
            )
            val regex = "(\\[f_static_)\\d{1,3}(\\])"
            val p = Pattern.compile(regex)
            val m = p.matcher(checkStr)
            return m.matches()
        }
        return false
    }

    private fun delete() {
        if (binding.chatMessage.text.isNotEmpty()) {
            val iCursorEnd = Selection.getSelectionEnd(binding.chatMessage.text)
            val iCursorStart = Selection.getSelectionStart(binding.chatMessage.text)
            if (iCursorEnd > 0) {
                if (iCursorEnd == iCursorStart) {
                    if (isDeletePng(iCursorEnd)) {
                        val st = "[f_static_000]"
                        (binding.chatMessage.text as Editable).delete(
                            iCursorEnd - st.length, iCursorEnd
                        )
                    } else {
                        (binding.chatMessage.text as Editable).delete(
                            iCursorEnd - 1,
                            iCursorEnd
                        )
                    }
                } else {
                    (binding.chatMessage.text as Editable).delete(
                        iCursorStart,
                        iCursorEnd
                    )
                }
            }
        }
    }


    private fun insert(text: CharSequence) {
        val iCursorStart = Selection.getSelectionStart(binding.chatMessage.text)
        val iCursorEnd = Selection.getSelectionEnd(binding.chatMessage.text)
        if (iCursorStart != iCursorEnd) {
            (binding.chatMessage.text as Editable).replace(iCursorStart, iCursorEnd, "")
        }
        val iCursor = Selection.getSelectionEnd(binding.chatMessage.text)
        (binding.chatMessage.text as Editable).insert(iCursor, text)
    }


    //---------------------------------这个是表情的删除按键。
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Emotion) {
        if (event.isDelete) {
            delete()
        } else {
            insert(event.text!!)
        }
    }


    //另一个地方接收到的消息都集中再这里。
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(da: ChatBean) {
        chatMsg.add(da)
        chatAdapter.setData(da)
        //NetCmd.chatText(text, false)
        binding.rc.smoothScrollToPosition(chatMsg.size - 1)
    }



    override fun onStart() {
        EventBus.getDefault().register(this);
        super.onStart()
    }


    override fun onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop()
    }


}
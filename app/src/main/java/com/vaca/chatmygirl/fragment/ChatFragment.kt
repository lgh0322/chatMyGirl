package com.vaca.chatmygirl.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.data.MyStorage
import com.vaca.chatmygirl.databinding.FragmentChatBinding
import com.vaca.chatmygirl.event.Emotion
import com.vaca.chatmygirl.net.NetCmd
import com.vaca.chatmygirl.utils.SoftHeight
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.regex.Pattern


class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding

    var keyboardVisible = false

    private fun showKeyboard(view: View) {
        val imm = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.requestFocus()
        imm.showSoftInput(view, 0)
    }

    private val topId = arrayOf(
        R.id.sendOptionFragment,
        R.id.faceFragment,
        R.id.voiceFragment,
    )
    lateinit var navController: NavController
    var currentIndex = 0
    lateinit var graph:NavGraph
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentChatBinding.inflate(inflater, container, false)


        val fm = childFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = fm.navController
        graph = navController.navInflater.inflate(R.navigation.send_navigation)


        val editText = binding.constraintLayout

        val lp = binding.container.layoutParams
        lp.height = MyStorage.keyboardHeightX
        binding.container.layoutParams = lp


        SoftHeight.observeSoftKeyboard(requireActivity(),
            object : SoftHeight.OnSoftKeyboardChangeListener {
                override fun onSoftKeyBoardChange(softKeybardHeight: Int, visible: Boolean) {
                    Log.e("gogo", softKeybardHeight.toString())
                    if (visible) {
                        val lp = binding.container.layoutParams
                        MyStorage.setKeyboardHeight(softKeybardHeight)
                        lp.height = softKeybardHeight
                        binding.container.layoutParams = lp
                        if (binding.container.visibility == View.VISIBLE) {
                            editText.setTranslationY(0f)
                        } else {
                            editText.setTranslationY(-softKeybardHeight.toFloat())
                        }

                    }
                    keyboardVisible = visible
                }

            })
        binding.messageSend.setOnClickListener {
            val text = binding.chatMessage.text.toString()
            if (text.isNotEmpty()) {
                NetCmd.chatText(text, false)
            }
        }

        binding.plus.setOnClickListener {
            jump(0)
        }

        binding.voice.setOnClickListener {
           jump(2)
        }

        binding.emotion.setOnClickListener {
           jump(1)
        }

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



    private fun jump(x:Int){
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
                    binding.container.visibility = View.VISIBLE
                    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        requireActivity().getCurrentFocus()?.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS
                    );
                }
            }

        } else {
            if (keyboardVisible) {
                binding.container.visibility = View.VISIBLE
                (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    requireActivity().getCurrentFocus()?.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
                );
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Emotion) {
        if (event.isDelete) {
            delete()
        } else {
            insert(event.text!!)
        }
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
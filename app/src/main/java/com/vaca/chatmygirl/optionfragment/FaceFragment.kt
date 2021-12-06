package com.vaca.chatmygirl.optionfragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.vaca.chatmygirl.R
import com.vaca.chatmygirl.adapter.FaceGVAdapter
import com.vaca.chatmygirl.adapter.FaceVPAdapter
import com.vaca.chatmygirl.databinding.FragmentFaceBinding
import com.vaca.chatmygirl.event.Emotion
import org.greenrobot.eventbus.EventBus
import java.util.*

class FaceFragment : Fragment() {





    lateinit var binding: FragmentFaceBinding
    lateinit var mDotsLayout: LinearLayout
    lateinit var mViewPager: ViewPager
    lateinit var staticFacesList: ArrayList<String>
    private val columns = 6
    private val rows = 4
    private val views: ArrayList<View> = ArrayList()

    inner class PageChange : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(arg0: Int) {}
        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageSelected(arg0: Int) {
            for (i in 0 until mDotsLayout.getChildCount()) {
                mDotsLayout.getChildAt(i).setSelected(false)
            }
            mDotsLayout.getChildAt(arg0).setSelected(true)
        }
    }


    private fun getPagerCount(): Int {
        val count: Int = staticFacesList.size
        return if (count % (columns * rows - 1) == 0) count / (columns * rows - 1) else count / (columns * rows - 1) + 1
    }



    private fun delete() {
        EventBus.getDefault().post(Emotion(true))
    }

    private fun insert(text: CharSequence) {
        EventBus.getDefault().post(Emotion(false,text))
    }

    fun bitMapScale(bitmap: Bitmap, scale: Float): Bitmap {
        val matrix = Matrix()
        matrix.postScale(scale, scale) //长和宽放大缩小的比例
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.getWidth(),
            bitmap.getHeight(),
            matrix,
            true
        )
    }
    private fun getFace(png: String): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        try {
            /**
             * 经过测试，虽然这里tempText被替换为png显示，但是但我单击发送按钮时，获取到輸入框的内容是tempText的值而不是png
             * 所以这里对这个tempText值做特殊处理
             * 格式：#[face/png/f_static_000.png]#，以方便判斷當前圖片是哪一個
             */
            val tempText = "[$png]"
            sb.append(tempText)
            sb.setSpan(
                ImageSpan(
                    requireContext(),bitMapScale( BitmapFactory
                        .decodeStream(requireActivity().getAssets().open("face/png/$png.png")),2.5f)
                ), sb.length
                        - tempText.length, sb.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb
    }



    private fun viewPagerItem(position: Int): View {
        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout: View = inflater.inflate(R.layout.face_gridview, null) //表情布局
        val gridview = layout.findViewById<View>(R.id.chart_face_gv) as GridView

        /**
         * 注：因为每一页末尾都有一个删除图标，所以每一页的实际表情columns *　rows　－　1; 空出最后一个位置给删除图标
         */
        val subList: MutableList<String> = ArrayList()
        subList.addAll(
            staticFacesList
                .subList(
                    position * (columns * rows - 1),
                    if ((columns * rows - 1) * (position + 1) > staticFacesList
                            .size
                    ) staticFacesList.size else (columns
                            * rows - 1)
                            * (position + 1)
                )
        )
        /**
         * 末尾添加删除图标
         */
        subList.add("emotion_del_normal.png")
        val mGvAdapter = FaceGVAdapter(subList, requireContext())
        gridview.adapter = mGvAdapter
        gridview.numColumns = columns
        // 单击表情执行的操作
        gridview.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                try {
                    val png = ((view as LinearLayout).getChildAt(1) as TextView).text.toString()
                    if (!png.contains("emotion_del_normal")) { // 如果不是删除图标
                        insert(getFace(png))
                    } else {
                        delete()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        return gridview
    }

    private fun dotsItem(position: Int): ImageView? {
        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout: View = inflater.inflate(R.layout.dot_image, null)
        val iv = layout.findViewById<View>(R.id.face_dot) as ImageView
        iv.id = position
        return iv
    }

    private fun initStaticFaces() {
        try {
            staticFacesList = ArrayList<String>()
            val faces: Array<String> = requireActivity().getAssets().list("face/png")!!
            //将Assets中的表情名称转为字符串一一添加进staticFacesList
            for (i in faces.indices) {
                staticFacesList.add(faces[i])
            }
            //去掉删除图片
            staticFacesList.remove("emotion_del_normal.png")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun InitViewPager() {
        // 获取页数
        for (i in 0 until getPagerCount()) {
            views.add(viewPagerItem(i)!!)
            val params = ViewGroup.LayoutParams(32, 32)
            mDotsLayout.addView(dotsItem(i), params)
        }
        val mVpAdapter = FaceVPAdapter(views)
        mViewPager.setAdapter(mVpAdapter)
        mDotsLayout.getChildAt(0).isSelected = true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFaceBinding.inflate(inflater, container, false)

        initStaticFaces()
        mViewPager = binding.faceViewpager
        mViewPager.addOnPageChangeListener(PageChange())
        mDotsLayout = binding.faceDotsContainer
        InitViewPager()

        return binding.root
    }
}
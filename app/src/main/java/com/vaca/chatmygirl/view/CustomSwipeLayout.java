package com.vaca.chatmygirl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.core.view.MotionEventCompat;

import com.daimajia.swipe.SwipeLayout;

public class CustomSwipeLayout extends SwipeLayout {

    float positionX, positionY;

    OnClickItemListener onClickItemListener;
    private int touchSlop;

    public CustomSwipeLayout(Context context) {
        super(context);
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public CustomSwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public CustomSwipeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int eventMask = MotionEventCompat.getActionMasked(ev);

        switch (eventMask) {
            case MotionEvent.ACTION_DOWN:
                positionY = ev.getRawY();
                positionX = ev.getRawX();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (Math.abs(positionY - ev.getRawY()) < touchSlop && Math.abs(positionX - ev.getRawX()) < touchSlop) {
                    if (onClickItemListener != null) {
                        onClickItemListener.onClick(CustomSwipeLayout.this);
                    }
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onClick(View view);
    }

}

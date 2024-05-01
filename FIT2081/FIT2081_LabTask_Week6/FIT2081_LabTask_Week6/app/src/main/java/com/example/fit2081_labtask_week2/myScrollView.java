package com.example.fit2081_labtask_week2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class myScrollView extends ScrollView {
    public myScrollView(Context context) {
        super(context);
    }

    public myScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public myScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public myScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}

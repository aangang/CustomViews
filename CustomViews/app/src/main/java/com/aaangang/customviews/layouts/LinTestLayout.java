package com.aaangang.customviews.layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/11/5.
 */
public class LinTestLayout extends ViewGroup{

    public LinTestLayout(Context context){
        this(context,null);
    }

    public LinTestLayout(Context context, AttributeSet attrs){
        super(context,attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode =  MeasureSpec.getMode(heightMeasureSpec);

        int width = 0,height = 0;
        int count = getChildCount();
        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            int childHeight = child.getMeasuredHeight();
            int childWidth = child.getMeasuredWidth();
            height += childHeight;
            width =Math.max(measureWidth,childWidth);
        }

        setMeasuredDimension(measureWidthMode==MeasureSpec.EXACTLY?measureWidth:width,
                                measureHeightMode==MeasureSpec.EXACTLY?measureHeight:height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;
        int count = getChildCount();
        for(int i = 0;i<count;i++){
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            child.layout(0,top,width,top+height);
            top += height;
            top += height;
        }

    }







}

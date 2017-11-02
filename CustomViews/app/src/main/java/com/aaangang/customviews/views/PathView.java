package com.aaangang.customviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.aaangang.customviews.utils.Tools;

/**
 * Created by Administrator on 2017/11/1.
 */
public class PathView extends View {

    private int mWidth,mHeight;
    private Paint mPaint = new Paint();

    public PathView(Context context){
        this(context,null);
    }

    public PathView(Context context, AttributeSet attrs){
        super(context,attrs);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.moveTo(100,100);
        path.lineTo(200,200);
        path.lineTo(200,500);
        path.close();
        canvas.drawPath(path,mPaint);

    }



}

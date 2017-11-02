package com.aaangang.customviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/11/1.
 */
public class BezierPathView extends View {

    private int mWidth,mHeight;
    private Paint mPaint = new Paint();
    private Paint basePaint = new Paint();

    int centerX,centerY;
    PointF start,end,control;

    public BezierPathView(Context context){
        this(context,null);
    }

    public BezierPathView(Context context, AttributeSet attrs){
        super(context,attrs);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        basePaint.setColor(Color.GRAY);
        basePaint.setStrokeWidth(5);
        basePaint.setStyle(Paint.Style.STROKE);

        start = new PointF(0,0);
        end = new PointF(0,0);
        control = new PointF(0,0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        centerX = mWidth/2;
        centerY = mHeight/2;
        //初始化数据点和控制点
        start.x = centerX -200;
        start.y = centerY;

        end.x = centerX+200;
        end.y = centerY;

        control.x = centerX;
        control.y = centerY -100;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                control.x = event.getX();
                control.y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                control.x = event.getX();
                control.y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                control.x = centerX;
                control.y = centerY -100;
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制坐标
        canvas.translate(mWidth/2,mHeight/2);
        drawBaseLines(canvas);

        //回到原点
        canvas.translate(-mWidth/2,-mHeight/2);

        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(start.x,start.y,mPaint);
        canvas.drawPoint(end.x,end.y,mPaint);
        canvas.drawPoint(control.x,control.y,mPaint);
        mPaint.setStrokeWidth(5);
        canvas.drawLine(start.x,start.y,control.x,control.y,mPaint);
        canvas.drawLine(end.x,end.y,control.x,control.y,mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        Path path = new Path();
        path.moveTo(start.x,start.y);
        path.quadTo(control.x,control.y,end.x,end.y);
        canvas.drawPath(path,mPaint);

    }

    private void drawBaseLines(Canvas canvas){
        //canvas.translate(mWidth/2,mHeight/2);

        canvas.drawLine(0,-mHeight/2,0,mHeight/2,basePaint);
        canvas.drawLine(-mWidth/2,0,mWidth/2,0,basePaint);

        canvas.drawLine(mWidth/2-20,-20,mWidth/2,0,basePaint);
        canvas.drawLine(mWidth/2-20,20,mWidth/2,0,basePaint);

        canvas.drawLine(-20,mHeight/2-20,0,mHeight/2,basePaint);
        canvas.drawLine(0,mHeight/2,20,mHeight/2-20,basePaint);
        int tmpWid=0,tmpHgt=0;
        for(int i = 0;tmpWid<mWidth/2;i++){
            canvas.drawLine(tmpWid,0,tmpWid,-20,basePaint);
            canvas.drawLine(-tmpWid,0,-tmpWid,-20,basePaint);
            tmpWid +=50;
        }
        for(int i = 0;tmpHgt<mHeight/2;i++){
            canvas.drawLine(0,-tmpHgt,20,-tmpHgt,basePaint);
            canvas.drawLine(0,tmpHgt,20,tmpHgt,basePaint);
            tmpHgt +=50;
        }
    }


}

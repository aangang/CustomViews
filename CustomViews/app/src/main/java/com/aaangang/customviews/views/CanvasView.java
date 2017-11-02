package com.aaangang.customviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.aaangang.customviews.models.PieData;
import com.aaangang.customviews.utils.Tools;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/1.
 */
public class CanvasView extends View {

    private int mWidth,mHeight;
    private Paint mPaint = new Paint();

    public CanvasView(Context context){
        this(context,null);
    }

    public CanvasView(Context context, AttributeSet attrs){
        super(context,attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
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
        // 省略了创建画笔的代码
        // 在坐标原点绘制一个黑色圆形
        mPaint.setColor(Color.BLACK);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);
        // 在坐标原点绘制一个蓝色圆形
        mPaint.setColor(Color.BLUE);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);

        Tools.log("width:" + mWidth + "   hight:" + mHeight);
        //缩放的中心默认为坐标原点,而缩放中心轴就是坐标轴
        // 将坐标系原点移动到画布正中心
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        canvas.translate(-400, -400); //回到原点
        canvas.translate(mWidth / 2 - 200, mHeight / 2 +200);
        RectF rect = new RectF(0,-400,400,0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        //canvas.scale(0.5f,0.5f);                // 画布缩放
        //canvas.scale(0.5f,0.5f,200,0);          // 画布缩放  <-- 缩放中心向右偏移了200个单位
        canvas.scale(0.5f,0.5f,200,-200);
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);


        /*canvas.scale(2.0f,2.0f,0,0);
        // 将坐标系原点移动到画布正中心
        canvas.translate(0, 500);
        //RectF rect = new RectF(0,-400,400,0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.rotate(60);                     // 旋转60度 <-- 默认旋转中心为原点
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);*/


        // 将坐标系原点移动到画布正中心
        //canvas.translate(mWidth / 2, mHeight / 2);
        canvas.translate(0, 500);
        canvas.scale(2.0f,2.0f,0,0);
        canvas.drawCircle(0,0,200,mPaint);          // 绘制两个圆形
        canvas.drawCircle(0,0,180,mPaint);
        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
            canvas.drawLine(0,180,0,200,mPaint);
            canvas.rotate(10);
        }

        canvas.rotate(-10); // 转回去
        canvas.translate(0,300);
        canvas.skew(1,0);
        rect = new RectF(0,200,200,0);
        canvas.drawRect(rect,mPaint);
    }



}

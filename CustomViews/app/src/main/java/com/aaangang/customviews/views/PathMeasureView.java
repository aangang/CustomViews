package com.aaangang.customviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.aaangang.customviews.utils.Tools;

/**
 * Created by Administrator on 2017/11/1.
 */
public class PathMeasureView extends View {

    private int mWidth,mHeight;
    private Paint mPaint = new Paint();
    private Paint basePaint = new Paint();

    public PathMeasureView(Context context){
        this(context,null);
    }

    public PathMeasureView(Context context, AttributeSet attrs){
        super(context,attrs);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        basePaint.setColor(Color.GRAY);
        basePaint.setStrokeWidth(5);
        basePaint.setStyle(Paint.Style.STROKE);
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
        //绘制坐标
        canvas.translate(mWidth/2,mHeight/2);
        drawBaseLines(canvas);

        mPaint.setStrokeWidth(10);
        /*Path path = new Path();
        path.addRect(-200,-200,200,200, Path.Direction.CW);

        Path dst = new Path();
        dst.lineTo(-300, -300);
        PathMeasure measure = new PathMeasure(path,true);
        measure.getSegment(300,900,dst,true);//是否move to path 的 start点
        canvas.drawPath(dst,mPaint);*/

        Path path1 = new Path();
        path1.addRect(-100,-100,100,100, Path.Direction.CW);
        path1.addRect(-200,200,200,600, Path.Direction.CW);
        PathMeasure measure = new PathMeasure(path1, false);     // 将Path与PathMeasure关联

        Path dst1 = new Path();
        float len1 = measure.getLength();                       // 获得第一条路径的长度
        Tools.log("len1:" + len1);
        measure.getSegment(125,656,dst1,true);
        measure.nextContour();                                  // 跳转到下一条路径
        float len2 = measure.getLength();                       // 获得第二条路径的长度
        Tools.log("len2:" + len2);
        measure.getSegment(278,876,dst1,true);
        canvas.drawPath(dst1,mPaint);


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

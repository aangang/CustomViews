package com.aaangang.customviews.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.aaangang.customviews.R;

/**
 * Created by Administrator on 2017/11/1.
 */
public class MatrixCameraView extends View {

    private int mWidth,mHeight;
    private Paint mPaint = new Paint();
    private Paint basePaint = new Paint();

    private Matrix mMatrix;
    private Bitmap mBitmap;

    public MatrixCameraView(Context context){
        this(context,null);
    }

    public MatrixCameraView(Context context, AttributeSet attrs){
        super(context,attrs);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        basePaint.setColor(Color.GRAY);
        basePaint.setStrokeWidth(5);
        basePaint.setStyle(Paint.Style.STROKE);

        initBitmapAndMatrix(context);
    }

    void initBitmapAndMatrix (Context context){
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myhead);
        mMatrix = new Matrix();

        mMatrix.postScale(0.8f,0.8f);
        mMatrix.postTranslate(-400,-400);


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

        canvas.drawBitmap(mBitmap,mMatrix,null);


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

package com.aaangang.customviews.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.aaangang.customviews.R;

/**
 * Created by Administrator on 2017/11/1.
 */
public class MatrixView extends View {

    private int mWidth,mHeight;
    private Paint mPaint = new Paint();
    private Paint basePaint = new Paint();

    private Matrix mPolyMatrix;
    private Bitmap mBitmap;

    public MatrixView(Context context){
        this(context,null);
    }

    public MatrixView(Context context, AttributeSet attrs){
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
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        mPolyMatrix = new Matrix();
        float[] src = {
                0,0,
                mBitmap.getWidth(),0,
                0,mBitmap.getHeight(),
                mBitmap.getWidth(),mBitmap.getHeight()
        };
        float[] dst = {
                0,0,
                mBitmap.getWidth(),400,
                0,mBitmap.getHeight(),
                mBitmap.getWidth(),mBitmap.getHeight()-200
        };

        mPolyMatrix.setPolyToPoly(src,0,dst,0,src.length >>1);

        mPolyMatrix.postScale(0.3f,0.3f);
        mPolyMatrix.postTranslate(-500,-500);


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

        canvas.drawBitmap(mBitmap,mPolyMatrix,null);


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

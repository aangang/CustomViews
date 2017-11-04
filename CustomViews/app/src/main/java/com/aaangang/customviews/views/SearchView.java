package com.aaangang.customviews.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.aaangang.customviews.utils.Tools;

/**
 * Created by Administrator on 2017/11/1.
 */
public class SearchView extends View {

    private int mWidth,mHeight;
    private Paint mPaint = new Paint();
    private Paint basePaint = new Paint();

    Path pathSearch,pathCircle;
    PathMeasure mMeasure;

    private int mDuration = 2000;
    private float mCurrentValue = 0;

    int mCount = 0;

    ValueAnimator startAnimator,searchAnimator,endAnimator;
    ValueAnimator.AnimatorUpdateListener updateListener;
    Animator.AnimatorListener animatorListener;

    public enum STATE {
        NONE,STARTING,SEARCHING,ENDING
    }
    STATE mState = STATE.NONE;

    public SearchView(Context context){
        this(context,null);
    }

    public SearchView(Context context, AttributeSet attrs){
        super(context,attrs);
        initAll();
    }

    void initAll(){
        initPaint();
        initPath();
        initAnimListener();
        initAnimator();

        mState = STATE.STARTING;
        startAnimator.start();
    }

    void initPaint(){
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(25);
        basePaint.setColor(Color.GRAY);
        basePaint.setStrokeWidth(5);
        basePaint.setStyle(Paint.Style.STROKE);
    }

    void initPath(){
        pathSearch = new Path();
        RectF rect = new RectF(-100,-100,100,100);
        pathSearch.addArc(rect,45,359.9f);// 注意,不要到360度,否则内部会自动优化,测量不能取到需要的数值
        pathCircle = new Path();
        rect = new RectF(-200,-200,200,200);
        pathCircle.addArc(rect,45,-359.9f);

        mMeasure = new PathMeasure();
        mMeasure.setPath(pathCircle,false);
        float[] pos = new float[2];
        mMeasure.getPosTan(0,pos,null);
        Tools.log("pos  x:" + pos[0] + "   y:" + pos[1]);
        pathSearch.lineTo(pos[0],pos[1]);

    }

    void initAnimListener(){
        updateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentValue = (float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        };
        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Tools.log("onAnimationStart");
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                Tools.log("onAnimationEnd");
                mStateHandler.sendEmptyMessage(0);
            }
            @Override
            public void onAnimationCancel(Animator animator) {
                Tools.log("onAnimationCancel");
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
                Tools.log("onAnimationRepeat");
            }
        };
    }

    void initAnimator(){
        startAnimator = ValueAnimator.ofFloat(0,1).setDuration(mDuration);
        startAnimator.addUpdateListener(updateListener);
        startAnimator.addListener(animatorListener);

        searchAnimator = ValueAnimator.ofFloat(0,1).setDuration(mDuration);
        searchAnimator.addUpdateListener(updateListener);
        searchAnimator.addListener(animatorListener);

        endAnimator = ValueAnimator.ofFloat(1,0).setDuration(mDuration);
        endAnimator.addUpdateListener(updateListener);
        endAnimator.addListener(animatorListener);

    }

    Handler mStateHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(mState == STATE.NONE){

            }else if(mState == STATE.STARTING){
                mState = STATE.SEARCHING;
                startAnimator.removeAllListeners();
                searchAnimator.start();
                Tools.toast(getContext(),"starting state over");
            }else if(mState == STATE.SEARCHING){
                searchAnimator.start();
                mCount ++;
                if(mCount>=3){
                    mState = STATE.ENDING;
                    searchAnimator.removeAllListeners();
                    endAnimator.start();
                    Tools.toast(getContext(),"search state over");
                }
            }else if(mState == STATE.ENDING){
                mState = STATE.STARTING;
                Tools.toast(getContext(),"ending state over");
                endAnimator.removeAllListeners();
                initAnimator();   //重新添加 监听器
                startAnimator.start();
                mCount=0;
            }

        }
    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#0058da"));
        //绘制坐标
        canvas.translate(mWidth/2,mHeight/2);
        drawBaseLines(canvas);

        //canvas.drawPath(pathSearch,mPaint);
        //canvas.drawPath(pathCircle,mPaint);

        drawSearchPath(canvas);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        startAnimator.removeAllListeners();
        startAnimator.cancel();
        searchAnimator.removeAllListeners();
        searchAnimator.cancel();
        endAnimator.removeAllListeners();
        endAnimator.cancel();


    }

    void drawSearchPath(Canvas canvas){
        if(mState == STATE.STARTING){
            mMeasure.setPath(pathSearch,false);
            Path dst = new Path();
            mMeasure.getSegment(mMeasure.getLength() * mCurrentValue,mMeasure.getLength(),dst,true);
            canvas.drawPath(dst,mPaint);
        }else if(mState == STATE.SEARCHING){
            mMeasure.setPath(pathCircle, false);
            Path dst2 = new Path();
            float stop = mMeasure.getLength() * mCurrentValue;
            float start = (float) (stop - ((0.5 - Math.abs(mCurrentValue - 0.5)) * 300f));
            mMeasure.getSegment(start, stop, dst2, true);
            canvas.drawPath(dst2,mPaint);
        }else if(mState == STATE.ENDING){
            mMeasure.setPath(pathSearch,false);
            Path dst3 = new Path();
            mMeasure.getSegment(mMeasure.getLength() * mCurrentValue,mMeasure.getLength(),dst3,true);
            canvas.drawPath(dst3,mPaint);
        }

    }

    public STATE getState(){
        return mState;
    }

    public void setState(STATE state){
        mState = state;
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

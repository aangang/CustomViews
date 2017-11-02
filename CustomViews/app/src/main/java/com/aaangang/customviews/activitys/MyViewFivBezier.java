package com.aaangang.customviews.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.aaangang.customviews.R;
import com.aaangang.customviews.views.BezierPathView;
import com.aaangang.customviews.views.PathView;

/**
 * Created by Administrator on 2017/11/2.
 */
public class MyViewFivBezier extends Activity{

    BezierPathView bezier_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_view);
        initView();
    }

    private void initView(){
        bezier_view = (BezierPathView)findViewById(R.id.bezier_view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

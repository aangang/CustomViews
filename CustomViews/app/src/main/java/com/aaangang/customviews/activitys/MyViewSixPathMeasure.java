package com.aaangang.customviews.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.aaangang.customviews.R;
import com.aaangang.customviews.views.BezierPathView;
import com.aaangang.customviews.views.PathMeasureView;

/**
 * Created by Administrator on 2017/11/2.
 */
public class MyViewSixPathMeasure extends Activity{

    PathMeasureView path_measure_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathmeasure);
        initView();
    }

    private void initView(){
        path_measure_view = (PathMeasureView)findViewById(R.id.path_measure_view);
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

package com.aaangang.customviews.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.aaangang.customviews.R;
import com.aaangang.customviews.views.MatrixCameraView;
import com.aaangang.customviews.views.MatrixView;

/**
 * Created by Administrator on 2017/11/2.
 */
public class MyViewNighMitrixCamera extends Activity{

    MatrixCameraView matrix_camera_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nigh_matrix_camera);
        initView();
    }

    private void initView(){
        matrix_camera_view = (MatrixCameraView)findViewById(R.id.matrix_camera_view);
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

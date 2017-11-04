package com.aaangang.customviews.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.aaangang.customviews.R;
import com.aaangang.customviews.views.MatrixView;
import com.aaangang.customviews.views.SearchView;

/**
 * Created by Administrator on 2017/11/2.
 */
public class MyViewEightMitrix extends Activity{

    MatrixView matrix_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight_matrix);
        initView();
    }

    private void initView(){
        matrix_view = (MatrixView)findViewById(R.id.matrix_view);
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

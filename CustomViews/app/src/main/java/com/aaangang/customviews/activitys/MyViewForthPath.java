package com.aaangang.customviews.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.aaangang.customviews.R;
import com.aaangang.customviews.views.CanvasPictureView;
import com.aaangang.customviews.views.PathView;

/**
 * Created by Administrator on 2017/11/2.
 */
public class MyViewForthPath extends Activity{

    PathView path_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forth_view);
        initView();
    }

    private void initView(){
        path_view = (PathView)findViewById(R.id.path_view);
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

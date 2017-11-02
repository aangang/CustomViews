package com.aaangang.customviews.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.aaangang.customviews.R;
import com.aaangang.customviews.utils.Tools;
import com.aaangang.customviews.views.CanvasView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/2.
 */
public class MyViewScd extends Activity{
    CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scd_view);
        canvasView = (CanvasView) findViewById(R.id.canvas_view);
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

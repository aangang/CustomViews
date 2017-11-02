package com.aaangang.customviews.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.aaangang.customviews.R;
import com.aaangang.customviews.views.CanvasPictureView;
import com.aaangang.customviews.views.CanvasView;

/**
 * Created by Administrator on 2017/11/2.
 */
public class MyViewTrd extends Activity{
    CanvasPictureView canvasPictView;

    Button check,uncheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trd_view);
        canvasPictView = (CanvasPictureView) findViewById(R.id.canvas_picture_view);
        initView();
    }

    private void initView(){
        check = (Button)findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvasPictView.check();
            }
        });

        uncheck = (Button)findViewById(R.id.uncheck);
        uncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvasPictView.unCheck();
            }
        });
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

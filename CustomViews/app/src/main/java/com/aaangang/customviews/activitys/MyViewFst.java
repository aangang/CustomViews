package com.aaangang.customviews.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.aaangang.customviews.R;
import com.aaangang.customviews.models.PieData;
import com.aaangang.customviews.utils.Tools;
import com.aaangang.customviews.views.PieView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/2.
 */
public class MyViewFst extends Activity{
    PieView pieView;
    ArrayList<PieData> pieDatas = new ArrayList<PieData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fst_view);
        pieView = (PieView) findViewById(R.id.pie_view);
        for(int i=0;i<5;i++){
            PieData data = new PieData(i+"",i);
            pieDatas.add(data);
        }
        Tools.log("pieDatas size:" + pieDatas.size());
        pieView.setData(pieDatas);
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

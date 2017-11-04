package com.aaangang.customviews.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.aaangang.customviews.R;
import com.aaangang.customviews.views.PathMeasureView;
import com.aaangang.customviews.views.SearchView;

/**
 * Created by Administrator on 2017/11/2.
 */
public class MyViewSevSearchPath extends Activity{

    SearchView search_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sev_search);
        initView();
    }

    private void initView(){
        search_view = (SearchView)findViewById(R.id.search_view);
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

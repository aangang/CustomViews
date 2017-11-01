package com.aaangang.customviews.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aaangang.customviews.R;

/**
 * Created by Administrator on 2017/11/2.
 */
public class Tools {
    public static final String TAG = "myview";

    public static void log(String string){
        Log.i(TAG,string);
    }

    public static void toast(Context context,String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}

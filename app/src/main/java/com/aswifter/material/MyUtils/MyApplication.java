package com.aswifter.material.MyUtils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2015/11/05.
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext()
    {
        return context;
    }
}

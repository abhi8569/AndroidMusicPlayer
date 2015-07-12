package com.abhi8569.musicplayer;

import android.app.Application;
import android.content.Context;

/**
 * Created by abishek on 12-07-2015.
 */
public class MyApp extends Application {

    private static Context mContext;



    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //  instance = this;
        mContext = getApplicationContext();
    }
}

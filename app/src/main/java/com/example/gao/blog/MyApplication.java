package com.example.gao.blog;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by gao on 2016/8/8.
 */
public class MyApplication extends LitePalApplication {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}

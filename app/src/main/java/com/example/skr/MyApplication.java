package com.example.skr;

import android.app.Application;


import com.xuexiang.xui.XUI;

import org.litepal.LitePalApplication;
import org.litepal.util.Const;

import java.util.HashMap;


public class MyApplication extends Application {

    public static HashMap<String , Object> infoMap = new HashMap<String, Object>();

    private static MyApplication mApp;

    public static MyApplication getInstance(){
        return mApp;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;

        XUI.init(this);
        XUI.debug(true);
        LitePalApplication.initialize(this);
    }
}

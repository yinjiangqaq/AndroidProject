package com.example.skr;

import android.app.Application;


import com.xuexiang.xui.XUI;

import org.litepal.LitePalApplication;
import org.litepal.util.Const;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);
        LitePalApplication.initialize(this);
    }
}

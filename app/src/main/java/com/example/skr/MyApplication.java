package com.example.skr;

import android.app.Application;

import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import org.litepal.LitePalApplication;
import org.litepal.util.Const;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        QMUISwipeBackActivityManager.init(this);
        LitePalApplication.initialize(this);
    }
}

package com.example.skr;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;


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

    //设置Activity对应的顶部状态栏的颜色
    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.example.skr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private  static boolean isExit = false;//实现点击两次返回键退出程序的功能

    String useraccount;

    public String getUseraccount(){
        return useraccount;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            isExit=false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.

//        Intent intent=getIntent();                                    //因为退出登录和切换账号都是用intent直接切过去，相当于切回login，但是从login登录到mainActivity的时候，并没有执行
                                                                        //onCreate，而是执行onStart，因为这个页面不是第一次创建的。所以我们要把拿到操作人的这个getIntent操作放在onStart
                                                                        //里面，不然下次登录还是显示的是之前的用户。并没有拿到新用户的userAccount,因为onCreate不执行
//        String userAccount=intent.getStringExtra("userAccount");
//
//        useraccount=userAccount;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=getIntent();
        String userAccount=intent.getStringExtra("userAccount");
        useraccount=userAccount;
        Log.d("find bug", "userAccount mainActivity at onStart: "+userAccount);
    }

    @Override//实现点击两次返回键就退出应用的代码
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private  void exit(){
        if(!isExit){
            isExit=true;
            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(0,2000);
        }else {
//            finish();
            finishAffinity();//把所有活动都finish了，然后下面系统退出,返回主界面
            System.exit(0);
        }
    }


}

package com.example.skr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.edittext.PasswordEditText;
import com.xuexiang.xui.widget.toast.XToast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class login extends AppCompatActivity{

    Button signIn_signInButton,
            signIn_signUpButton,
            signIn_forgetPasswordButton,
            signUp_signUpButton,
            signUn_goBackButton;
    LinearLayout signIn_view, signUp_view;
    EditText signIn_account,
            signUp_account;
//             signIn_password,
//             signUp_password;
    PasswordEditText signIn_password, signUp_password;
//    TextView signIn_tip,signUp_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);

        MyApplication.setWindowStatusBarColor(this,R.color.Black);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Connector.getDatabase();

        setInitialNumber();

        signIn_signInButton = (Button) findViewById(R.id.signIn_signInButton);
        signIn_signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickIIB();
            }
        });
        signIn_signUpButton = (Button) findViewById(R.id.signIn_signUpButton);
        signIn_signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickIUB();
            }
        });
        signIn_forgetPasswordButton = (Button) findViewById(R.id.signIn_forgetPasswordButton);
        signIn_forgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickIFP();
            }
        });
        signUp_signUpButton = (Button) findViewById(R.id.signUp_signUpButton);
        signUp_signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickUUB();
            }
        });
        signUn_goBackButton = (Button) findViewById(R.id.signUn_goBackButton);
        signUn_goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickUGB();
            }
        });

        signIn_view = (LinearLayout) findViewById(R.id.signIn_view);
        signUp_view = (LinearLayout) findViewById(R.id.signUp_view);

        signIn_account = (EditText) findViewById(R.id.signIn_account);
//        signIn_password = (EditText) findViewById(R.id.signIn_password);
        signUp_account = (EditText) findViewById(R.id.signUp_account);
//        signUp_password = (EditText) findViewById(R.id.signUp_password);

        signIn_password = (PasswordEditText) findViewById(R.id.signIn_password);
        signUp_password = (PasswordEditText) findViewById(R.id.signUp_password);

//        signIn_tip = (TextView) findViewById(R.id.signIn_tip);
//        signUp_tip = (TextView) findViewById(R.id.signUp_tip);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        becomeClear();
        signUp_view.setVisibility(View.GONE);
        signIn_view.setVisibility(View.VISIBLE);
    }

    public void becomeClear(){
        signIn_account.setText("");
        signIn_password.setText("");
        signUp_account.setText("");
        signUp_password.setText("");
//        signIn_tip.setVisibility(View.GONE);
//        signUp_tip.setVisibility(View.GONE);
    }

    public void ClickIIB(){
        List<user> userList;
        List<user> userList2;
        if (signIn_account.getText().toString().trim().isEmpty() || signIn_password.getText().toString().trim().isEmpty()){
            XToast.error(this,"请正确输入账号和密码").show();
//            signIn_tip.setText("请正确输入账号和密码");
//            signIn_tip.setTextColor(this.getResources().getColor(R.color.Red));
//            signIn_tip.setVisibility(View.VISIBLE);
        }
        else {
            userList = DataSupport
                    .where("userAccount=?",signIn_account.getText().toString())
                    .find(user.class);
            userList2 = DataSupport.findAll(user.class);

            Log.d("testMessage",Integer.toString(userList.size()));
            Log.d("testMessage",Integer.toString(userList2.size()));
            for (user u : userList){
                Log.d("testMessage",
                        u.getPassword()
                                +"--"+u.getUserAccount()
                                +"--"+u.getUserName()
                                +"--"+u.getUserIntro()
                                +"--"+u.getBirthday()
                                +"--"+u.getPortrait()
                );
            }
            if (userList==null||userList.size()==0){
                XToast.error(this,"查无此账号").show();
//                signIn_tip.setText("查无此账号");
//                signIn_tip.setTextColor(this.getResources().getColor(R.color.Red));
//                signIn_tip.setVisibility(View.VISIBLE);
            }
            else {
                user temp = userList.get(0);
                Log.d("testMessage",temp.getPassword() );
//                Log.d("testMessage",signIn_password.getText().toString() );
                if (signIn_password.getText().toString().equals(temp.getPassword())){
                    XToast.success(this,"登录成功").show();


//                    signIn_tip.setText("登录成功");
//                    signIn_tip.setTextColor(this.getResources().getColor(R.color.Green));
//                    signIn_tip.setVisibility(View.VISIBLE);


//                    MyApplication.infoMap.put("userAccount",signIn_account.getText().toString());

//                    signIn_signInButton.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    },500);
                    Intent intent = new Intent(login.this,MainActivity.class);
                    intent.putExtra("userAccount",signIn_account.getText().toString());
                    startActivity(intent);

//                    Intent intent = new Intent(login.this,MainActivity.class);
//                    startActivity(intent);

//                    Intent intent = new Intent(login.this,MainActivity.class);
//                    intent.putExtra("userAccount", signIn_account.getText().toString());
//                    startActivity(intent);


                }
                else {
                    XToast.error(this,"密码错误").show();
//                    signIn_tip.setText("密码错误");
//                    signIn_tip.setTextColor(this.getResources().getColor(R.color.Red));
//                    signIn_tip.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    public void ClickIUB(){
        becomeClear();
        signUp_view.setVisibility(View.VISIBLE);
        signIn_view.setVisibility(View.GONE);
    }
    public void ClickIFP(){
        XToast.info(this,"初始账号：20173068，密码：123456").show();
//        signIn_account.setText("20173068");
//        signIn_password.setText("123456");
//        signIn_tip.setText("初始账号：20173068，密码：123456");
//        signIn_tip.setTextColor(this.getResources().getColor(R.color.orange));
//        signIn_tip.setVisibility(View.VISIBLE);
    }
    public void ClickUUB(){
        List<user> userList;
        if (signUp_account.getText().toString().trim().isEmpty() || signUp_password.getText().toString().trim().isEmpty()){
            XToast.error(this,"请正确输入账号和密码").show();
//            signUp_tip.setText("请正确输入账号和密码");
//            signUp_tip.setTextColor(this.getResources().getColor(R.color.Red));
//            signUp_tip.setVisibility(View.VISIBLE);
        }
        else {
            userList = DataSupport
                    .where("userAccount=?",signUp_account.getText().toString())
                    .find(user.class);
            if (userList==null||userList.size()==0){
                user me = new user();
                me.setUserAccount(signUp_account.getText().toString());
                me.setPassword(signUp_password.getText().toString());
                me.setBirthday("2000-01-01");
                me.setSex("男");
                me.setUserName("新用户");
                me.setUserIntro("我的个性签名");
        //                me.setPortrait("");
                me.save();

                XToast.success(this,"注册成功").show();
//                signUp_tip.setText("注册成功");
//                signUp_tip.setTextColor(this.getResources().getColor(R.color.Green));
//                signUp_tip.setVisibility(View.VISIBLE);

//                MyApplication.infoMap.put("userAccount",signIn_account.getText().toString());

//                signUp_signUpButton.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                },1500);
                Intent intent = new Intent(login.this,MainActivity.class);
                intent.putExtra("userAccount",signUp_account.getText().toString());
                startActivity(intent);
                finish();

            }
            else {
                XToast.error(this,"该账号已被注册").show();
//                signUp_tip.setText("该账号已被注册");
//                signUp_tip.setTextColor(this.getResources().getColor(R.color.Red));
//                signUp_tip.setVisibility(View.VISIBLE);
            }
        }
    }

    public void ClickUGB(){
        becomeClear();
        signIn_view.setVisibility(View.VISIBLE);
        signUp_view.setVisibility(View.GONE);
    }


    public void setInitialNumber(){
        List<user> userList = DataSupport
                .where("userAccount=?","20173068")
                .find(user.class);
        if (userList==null||userList.size()==0){
            user me = new user();
            me.setUserAccount("20173068");
            me.setPassword("123456");
            me.setBirthday("2000/01/01");
            me.setSex("男");
            me.setUserName("初始账号");
            me.setUserIntro("我的个性签名");
            me.setPortrait("");
            me.save();
        }
    }

}

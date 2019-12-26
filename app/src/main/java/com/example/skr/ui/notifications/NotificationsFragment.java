package com.example.skr.ui.notifications;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.skr.MainActivity;
import com.example.skr.MyApplication;
import com.example.skr.R;
import com.example.skr.fan;
import com.example.skr.follow;
import com.example.skr.post;
import com.example.skr.set;
import com.example.skr.set_user_information;
import com.example.skr.user;

import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import org.litepal.util.Const;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class NotificationsFragment extends Fragment {
    String userAccount;
    private NotificationsViewModel notificationsViewModel;
    TextView userName;
    TextView userAccount1;
    ImageView picture;
    TextView fan;
    TextView follow;
    TextView fatie;
    List<user> userList;
    List<com.example.skr.fan> fans;
    List<com.example.skr.follow>follows;
    List<com.example.skr.post>faties;

    LinearLayout setinformation;
    LinearLayout set;

//    @Override

//    public void onAttach(Context context) {
//        super.onAttach(context);
//        userAccount = ((MainActivity)getActivity()).getUseraccount();
//    }

    private void requestWritePermission(){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        Connector.getDatabase();
        requestWritePermission();



        setinformation = root.findViewById(R.id.set_user_information);
        set=root.findViewById(R.id.set);
        fan=root.findViewById(R.id.fan);
        follow=root.findViewById(R.id.follow);
        fatie=root.findViewById(R.id.fatie);
        userName=root.findViewById(R.id.userName);
        userAccount1=root.findViewById(R.id.userAccount);
        picture=root.findViewById(R.id.image);

        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

//
//    @Override
//    public void onStart() {
//        super.onStart();
//    }

    @Override
    public void onResume() {
        super.onResume();
        userAccount = ((MainActivity)getActivity()).getUseraccount();
        Log.d("find bug", "NotificationsFragment onresume: userAccount"+userAccount);

        userList= DataSupport.where("userAccount=?",userAccount).find(user.class);
        fans= DataSupport.where("userAccount=?",userAccount).find(fan.class);
        faties= DataSupport.where("userAccount=?",userAccount).find(post.class);
        follows= DataSupport.where("userAccount=?",userAccount).find(follow.class);
        if(fans.size()!=0) {
            fan.setText(fans.size() + "粉丝");
        }
        else{
            fan.setText("0粉丝");
        }
        if(follows.size()!=0)
        {
            follow.setText(follows.size()+"关注");
        }else{
            follow.setText("0关注");
        }
        if(faties.size()!=0)
        {
            fatie.setText(faties.size()+"发帖");
        }else{
            fatie.setText("0发帖");
        }
        final user user=userList.get(0);
        if(user.getUserName()!=null)
        {
            userName.setText(user.getUserName());
        }else{}
        if (user.getUserAccount()!=null){
            userAccount1.setText(user.getUserIntro());
        }
        if(user.getPortrait()!=null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(user.getPortrait());
            picture.setImageBitmap(bitmap);
        }
        else{}

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), com.example.skr.set.class);

                startActivity(intent);
            }
        });
        setinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("userAccount",userAccount);
                intent.setClass(getActivity(), set_user_information.class);
                startActivity(intent);
            }
        });

    }
}
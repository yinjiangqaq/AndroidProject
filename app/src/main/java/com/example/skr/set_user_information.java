package com.example.skr;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.skr.ui.notifications.NotificationsFragment;

import org.litepal.util.Const;

import java.io.FileNotFoundException;
import java.util.List;

public class set_user_information extends AppCompatActivity {
    public static final int CHOOSE_PHOTO=2;
    public  static  final  int TAKE_PHOTO=1;
    private ImageView picture;
    private Uri imageUri;
    String imagePath;
    Button save;
    EditText birthday;
    EditText userName;
    EditText sex;
    TextView userAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent=getIntent();

        setContentView(R.layout.set_user_information);
        picture=(ImageView)findViewById(R.id.picture);
        Button button=(Button)findViewById(R.id.save);

        Connector.getDatabase();
        List<user> userList;

        userAccount=(TextView) findViewById(R.id.userAccount);
        birthday=(EditText)findViewById(R.id.birthday);
        userName=(EditText)findViewById(R.id.userName);
        sex=(EditText) findViewById(R.id.sex);
        save=(Button)findViewById(R.id.save);
       // userAccount.setText((String)MyApplication.infoMap.get("userAccount"));
        userAccount.setText(intent.getStringExtra("userAccount"));
//        userAccount.setText(intent.getStringExtra("userAccount"));

        Log.d("set_user_info", "infoMap.get('userAccount'):"+MyApplication.infoMap.get("userAccount"));
        Log.d("set_user_info", "userAccount:"+userAccount.getText().toString());
        if (TextUtils.isEmpty((String)MyApplication.infoMap.get("userAccount"))){
            Log.d("set_user_info", "infoMap.get('userAccount') is null or '' :"+MyApplication.infoMap.get("userAccount"));
        }else {
            Log.d("set_user_info", "infoMap.get('userAccount') is not null or '' :"+MyApplication.infoMap.get("userAccount"));
        }

        userList=DataSupport.where("userAccount=?",userAccount.getText().toString()).find(user.class);
        final user user=userList.get(0);
        if(user.getBirthday()!=null)
        {
            birthday.setText(user.getBirthday());
        }
        else
        { }
        if(user.getUserName()!=null)
        {
            userName.setText(user.getUserName());
        }
        else {}
        if(user.getSex()!=null)
        {
            sex.setText(user.getSex());
        }else {}
        if(user.getPortrait()!=null)
        {

            Bitmap bitmap = BitmapFactory.decodeFile(user.getPortrait());
            picture.setImageBitmap(bitmap);
        }
        else{}


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user user1=new user();
                user1.setBirthday(birthday.getText().toString());
                user1.setSex(sex.getText().toString());
                user1.setUserName(userName.getText().toString());
                user1.setPortrait(imagePath);
                user1.updateAll("userAccount=?", userAccount.getText().toString());
               // Intent intent1=new Intent();
//                intent1.putExtra("userAccount",userAccount.getText().toString());
//                intent1.setClass(set_user_information.this,MainActivity.class);

                finish();
            //    startActivity(intent1);
            }
        });




        picture .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(set_user_information.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(set_user_information.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
                else{
                    openAlbum();
                }
            }
        });
    }


    private void openAlbum()
    {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)
    {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT);
                }
                break;
            default:
        }

    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19)
                        handleImageOnKitKat(data);
                    else
                        handleImageBeforeKitKat(data);
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data)
    {
        imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri))
        {
            String docID=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority()))
            {
                String id=docID.split(":")[1];
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }
            else if("com.android.providers.downloads.documents".equals(uri.getAuthority()))
            {
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docID));
                imagePath=getImagePath(contentUri,null);
            }
        }
        else if("content".equalsIgnoreCase(uri.getScheme()))
        {
            imagePath=getImagePath(uri,null);
        }
        else if("file".equalsIgnoreCase(uri.getScheme()))
        {
            imagePath=uri.getPath();
        }

        displayImage(imagePath);
    }
    private void handleImageBeforeKitKat(Intent data)
    {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);

        displayImage(imagePath);
    }
    private  String getImagePath(Uri uri,String selection)
    {
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }

        return path;
    }
    private void  displayImage(String imagepath)
    {
        if(imagepath!=null)
        {
            if (Build.VERSION.SDK_INT >= 23) {
                int REQUEST_CODE_CONTACT = 101;
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //验证是否许可权限
                for (String str : permissions) {
                    if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限
                        this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                        return;
                    }
                }
            }
            BitmapFactory.Options op = new BitmapFactory.Options();

            op.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath,op);
            picture.setImageBitmap(bitmap);
            Toast.makeText(this, imagepath, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"fail to get image",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 解决Android 6.0 或以上版本不能读取外部存储权限的问题，哪里需要读写SD卡的权限，就调用这个方法，必须在一个Activity里
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
            return false;
        }
        return true;

    }
}
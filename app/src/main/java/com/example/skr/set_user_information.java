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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.FileNotFoundException;

public class set_user_information extends AppCompatActivity {
    public static final int CHOOSE_PHOTO=2;
    public  static  final  int TAKE_PHOTO=1;
    private ImageView picture;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_user_information);
        picture=(ImageView)findViewById(R.id.picture);
        Button button=(Button)findViewById(R.id.save);


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
        String imagePath=null;
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
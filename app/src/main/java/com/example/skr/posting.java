package com.example.skr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.edittext.ClearEditText;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.toast.XToast;

import org.litepal.tablemanager.Connector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.litepal.LitePalApplication.getContext;

public class posting extends AppCompatActivity {


    public static final int CHOOSE_PHOTO=2;
    public  static  final  int TAKE_PHOTO=1;
    private Uri imageUri;
    private String posting_imagePath;
    private String userAccount;//操作人
    private ClearEditText posting_title;
    private MultiLineEditText posting_content;
    private ImageView posting_selectImageButton,posting_selectImage;
    private Button posting_postButton;
    private boolean lock=false;
//    private String[] imagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        MyApplication.setWindowStatusBarColor(this,R.color.Black);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userAccount= intent.getStringExtra("userAccount");//操作本人的
        setContentView(R.layout.activity_posting);

        Connector.getDatabase();

        posting_title = (ClearEditText) findViewById(R.id.posting_title);
        posting_content = (MultiLineEditText) findViewById(R.id.posting_content);
        posting_selectImageButton = (ImageView) findViewById(R.id.posting_selectImageButton);
        posting_selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock){
                    //do nothing
                }
                else{
                    new BottomSheet.BottomListSheetBuilder(posting.this)
                            .addItem("相册")
                            .addItem("拍照")
                            .setOnSheetItemClickListener(new BottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                                @Override
                                public void onClick(BottomSheet dialog, View itemView, int position, String tag) {
                                    if(position+1==1){
//                                    Toast.makeText(posting.this,"选择相册",Toast.LENGTH_SHORT).show();

                                        if(ContextCompat.checkSelfPermission(posting.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(posting.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                                        }
                                        else{
                                            openAlbum();
                                        }

                                    }else if(position+1==2){
                                        XToast.info(posting.this,"拍照功能未实现").show();
//                                        File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
//                                        try {
//                                            if(outputImage.exists()){
//                                                outputImage.delete();
//                                            }
//                                            outputImage.createNewFile();
//                                        }catch (IOException e){
//                                            e.printStackTrace();
//                                        }
//                                        if (Build.VERSION.SDK_INT>=24){
//                                            imageUri = FileProvider.getUriForFile(posting.this,"com.example.skr.fileprovider",outputImage);
//                                        }else {
//                                            imageUri = Uri.fromFile(outputImage);
//                                        }
//                                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//                                        startActivityForResult(intent,TAKE_PHOTO);
                                    }
                                    dialog.dismiss();
                                }
                            })
                            .build()
                            .show();
                }
            }
        });
        posting_selectImage = (ImageView) findViewById(R.id.posting_selectImage);
        posting_postButton = (Button) findViewById(R.id.posting_postButton);
        posting_postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock){
                    //do nothing
                }
                else{
                    if(posting_title.getText().toString().trim().isEmpty()||posting_content.isEmpty()){
                        XToast.warning(posting.this,"请完善信息").show();
                    }
                    else {
                        lock=true;
                        //存储帖子，页面跳转
//                        String userAccount = (String) MyApplication.infoMap.get("userAccount");
                        post myNewPost = new post();
                        myNewPost.setPost_id(UUID.randomUUID().toString());
                        myNewPost.setUserAccount(userAccount);
                        myNewPost.setPost_title(posting_title.getText().toString());
                        myNewPost.setPost_content(posting_content.getContentText());
                        myNewPost.setPost_image(posting_imagePath);
                        myNewPost.setPost_time(MyApplication.getNowTime());
                        myNewPost.save();

                        XToast.success(posting.this,"发帖成功").show();

                        posting_postButton.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },500);

                    }
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
                    XToast.error(posting.this,"获取授权失败").show();
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
                        posting_selectImage.setImageBitmap(bitmap);
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
        if(DocumentsContract.isDocumentUri(this,uri)) {
            String docID = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id=docID.split(":")[1];
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            } else if("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docID));
                imagePath = getImagePath(contentUri,null);
            }
        } else if("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath=getImagePath(uri,null);
        } else if("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath=uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    private  String getImagePath(Uri uri,String selection) {
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

    private void  displayImage(String imagepath) {
        if(imagepath!=null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            posting_imagePath=imagepath;
            posting_selectImage.setImageBitmap(bitmap);
        }
        else {
            XToast.error(posting.this,"获取图片失败").show();
        }
    }
}

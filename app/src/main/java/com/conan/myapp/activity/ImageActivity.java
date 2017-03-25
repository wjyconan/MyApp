package com.conan.myapp.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.conan.myapp.BaseActivity;
import com.conan.myapp.R;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author        conan
 * PublishDate   17/3/23
 * Description   图片拍照和选择相册界面
 * Version       1.0
 * Updated       conan
 */
public class ImageActivity extends BaseActivity implements View.OnClickListener {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ImageView imageView;

    private Uri imageUri;

    private static final String TAG = "ImageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        Button btnCamera = (Button) findViewById(R.id.camera);
        Button btnPhoto = (Button) findViewById(R.id.photo);
        imageView = (ImageView) findViewById(R.id.imageView);

        btnCamera.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                File outputImage = new File(getExternalCacheDir(), "outputImage.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(this, "com.conan.myapp.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;
            case R.id.photo:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                        Log.d(TAG, "onActivityResult: imageUri---->" + imageUri);
                        Glide.with(this).load(imageUri).into(imageView);
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                        imageView.setImageBitmap(bitmap);
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imageUrl = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document 类型的Uri,则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];  // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imageUrl = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));
                imageUrl = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的uri，则使用普通方式处理
            imageUrl = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri,直接获取图片路径即可
            imageUrl = uri.getPath();
        }
        displayImage(imageUrl);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imageUrl = getImagePath(uri, null);
        displayImage(imageUrl);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真是的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imageUrl) {
        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).into(imageView);
//            Bitmap bitmap = BitmapFactory.decodeFile(imageUrl);
//            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "fail to get image!", Toast.LENGTH_SHORT).show();
        }
    }
}

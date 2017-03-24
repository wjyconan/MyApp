package com.conan.myapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadDemoActivity extends BaseActivity implements ServiceConnection {

    private static final String TAG = "DownloadDemoActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private DownloadService.DownloadBinder downloadBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_demo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, this, BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @OnClick({R.id.btn_start, R.id.btn_pause, R.id.btn_cancel})
    public void onClick(View view) {
        if (downloadBinder == null) {
            Log.d(TAG, "onClick: downloadBinder == null");
            return;
        }
        switch (view.getId()) {
            case R.id.btn_start:
                String url = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
                downloadBinder.startDownload(url);
                break;
            case R.id.btn_pause:
                downloadBinder.pauseDownload();
                break;
            case R.id.btn_cancel:
                downloadBinder.cancelDownload();
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "onServiceConnected: success");
        downloadBinder = (DownloadService.DownloadBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }
}

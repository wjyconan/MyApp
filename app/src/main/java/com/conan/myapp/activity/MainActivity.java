package com.conan.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.conan.myapp.BaseActivity;
import com.conan.myapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.btn_image, R.id.btn_web, R.id.btn_okhttp, R.id.btn_service, R.id.btn_download_demo,R.id.btn_ui,R.id.btn_canvas})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_image:
                intent = new Intent(this, ImageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_web:
                intent = new Intent(this, WebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_okhttp:
                intent = new Intent(this, OkHttpActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_service:
                intent = new Intent(this, ServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_download_demo:
                intent = new Intent(this, DownloadDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_ui:
                intent = new Intent(this, UIActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_canvas:
                intent = new Intent(this, CanvasActivity.class);
                startActivity(intent);
                break;
        }
    }
}

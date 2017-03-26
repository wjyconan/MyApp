package com.conan.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.conan.myapp.BaseActivity;
import com.conan.myapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollapsingActivity extends BaseActivity {

    public static final String STAR_NAME = "star_name";
    public static final String STAR_IMG_URL = "star_img_url";

    @BindView(R.id.img_star)
    ImageView imgStar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ctlayout)
    CollapsingToolbarLayout ctlayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.txt_star_name)
    TextView txtStarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String starName = intent.getStringExtra(STAR_NAME);
        String starImgUrl = intent.getStringExtra(STAR_IMG_URL);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ctlayout.setTitle(starName);
        Glide.with(this).load(starImgUrl).into(imgStar);
        txtStarName.setText(getContentText(starName));
    }

    private String getContentText(String starName) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            builder.append(starName);
        }
        return builder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.conan.myapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.conan.myapp.BaseActivity;
import com.conan.myapp.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author        conan
 * PublishDate   17/3/23
 * Description   测试OKHttp使用
 * Version       1.0
 * Updated       conan
 */
public class OkHttpActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;

    private static final String TAG = "OkHttpActivity";
    private static final int UPDATE_TEXT = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_TEXT:
                        Bundle data = msg.getData();
                        tv.setText(data.getString("response"));
                        break;
                }
            }
        };
    }

    @OnClick(R.id.btn_send)
    public void onClick() {

        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(OkHttpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("response", response.body().string());
                    Message message = new Message();
                    message.what = UPDATE_TEXT;
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

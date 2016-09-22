package com.bigbird.nfcsend;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bigbird.nfcsend.Utils.ActivityUtils;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button btn_send_file, btn_send_msg, btn_send_uri;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        clickView();
    }

    private void findView() {
        btn_send_msg = (Button) findViewById(R.id.btn_send_msg);
        btn_send_uri = (Button) findViewById(R.id.btn_send_uri);
        btn_send_file = (Button) findViewById(R.id.btn_send_file);
    }

    private void clickView() {
        btn_send_msg.setOnClickListener(this);
        btn_send_uri.setOnClickListener(this);
        btn_send_file.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send_msg:
                ActivityUtils.startActivity(this, SendMsgActivity.class);
                break;
            case R.id.btn_send_uri:
                ActivityUtils.startActivity(this, SendUriActivity.class);
                break;
            case R.id.btn_send_file:
                ActivityUtils.startActivity(this, SendFileActivity.class);
                break;
        }
    }
}

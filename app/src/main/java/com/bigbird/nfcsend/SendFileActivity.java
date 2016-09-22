package com.bigbird.nfcsend;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigbird.nfcsend.Utils.ToastUtils;
import com.bigbird.pickimg.Action;

import java.io.File;

/**
 * Created by xp on 2016/9/21.
 */
public class SendFileActivity extends Activity {
    private TextView tv_location;
    private Button btn_img;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_file);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        findView();
        clickView();
    }

    private void findView() {
        btn_img = (Button) findViewById(R.id.btn_img);
        tv_location = (TextView) findViewById(R.id.tv_location);
    }

    private void clickView() {
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Action.ACTION_PICK);
                startActivityForResult(i, 100);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
            String single_path = data.getStringExtra("single_path");
            final File fileToTransfer = new File(single_path);
            fileToTransfer.setReadable(true, false);
            tv_location.setText(Uri.fromFile(fileToTransfer).toString().trim());

            if(fileToTransfer.exists()){
                mNfcAdapter.setBeamPushUris(new Uri[]{Uri.parse("file://" +single_path)}, SendFileActivity.this);
            } else {
                ToastUtils.show(this, "图片路径有问题！");
            }
        }
    }
}
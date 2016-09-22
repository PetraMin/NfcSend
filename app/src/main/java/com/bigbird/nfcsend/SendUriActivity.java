package com.bigbird.nfcsend;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigbird.nfcsend.Utils.ToastUtils;

/**
 * Created by xp on 2016/9/21.
 */
public class SendUriActivity extends Activity
        implements NfcAdapter.CreateNdefMessageCallback,
        NfcAdapter.OnNdefPushCompleteCallback{
    private final static int SHOW_TOAST = 1;
    private EditText et_content;
    private TextView tv_content, tv_introduce;
    private NfcAdapter mNfcAdapter;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW_TOAST:
                    ToastUtils.show(SendUriActivity.this, "发送成功");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_uri);

        et_content = (EditText) findViewById(R.id.et_content);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_introduce = (TextView) findViewById(R.id.tv_introduce);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null){
            tv_introduce.setText(getString(R.string.not_nfc));
            et_content.setVisibility(View.GONE);
            tv_content.setVisibility(View.GONE);
        } else if (!mNfcAdapter.isEnabled()){
            tv_introduce.setText(getString(R.string.nfc_fail));
            et_content.setVisibility(View.GONE);
            tv_content.setVisibility(View.GONE);
        } else {
            tv_introduce.setText(getString(R.string.introduce));
            tv_content.setText(getString(R.string.content));
            tv_content.setVisibility(View.VISIBLE);
            et_content.setVisibility(View.VISIBLE);

            //设置监听
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
    }


    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        NdefMessage msg = new NdefMessage(NdefRecord.createUri(et_content.getText().toString().trim()));
        return msg;
    }

    @Override
    public void onNdefPushComplete(NfcEvent nfcEvent) {
        mHandler.sendEmptyMessage(SHOW_TOAST);
    }
}
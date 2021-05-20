package com.example.mywechat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PayActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout lin;
    private View view_custom;
    private AlertDialog.Builder builder = null;
    private AlertDialog alert = null;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        //初始化Builder
        builder = new AlertDialog.Builder(this);
        lin = findViewById(R.id.lin);

        textView = findViewById(R.id.text_get);
        toolbar = findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);

        //加载自定义的那个View,同时设置下
        final LayoutInflater inflater = PayActivity.this.getLayoutInflater();
        view_custom = inflater.inflate(R.layout.pay_dialog, null,false);
        builder.setView(view_custom);
        builder.setCancelable(false);
        alert = builder.create();
        editText = view_custom.findViewById(R.id.edit);

        view_custom.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        view_custom.findViewById(R.id.btn_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if(s!= null && !s.equals("")){
                    textView.setText(s);
                    Toast.makeText(getApplicationContext(), "充值成功！", Toast.LENGTH_SHORT).show();
                }
                alert.dismiss();
            }
        });

        view_custom.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "充值对话框已关闭~", Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });

        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }
}

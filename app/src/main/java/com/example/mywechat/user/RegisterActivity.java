package com.example.mywechat.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.mywechat.R;
import com.example.mywechat.tool.Dissloading;
import com.example.mywechat.tool.SqlHelper;
import com.example.mywechat.tool.ToolUtils;

/**
 * 用户注册类
 */
public class RegisterActivity extends AppCompatActivity {

    private ImageButton register;
    private EditText rgUser, rgPass, rgRePass;
    private Intent intent;
    private Toolbar toolbar;
    private SqlHelper sqlHelper;
    private Dissloading dialog;
    int REGISTETOLOGINR = 10002;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.register);
        init();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        //返回false表示点击后，隐藏软键盘;返回true表示保留软键盘.
        rgRePass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return false;
                }
                return true;
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    /**
     * 注册功能
     */
    private void attemptRegister() {
        // 重置错误
        rgUser.setError(null);
        rgPass.setError(null);
        rgRePass.setError(null);

        // 存储登录
        String userID = rgUser.getText().toString().trim();
        String password = rgPass.getText().toString().trim();
        String rPass = rgRePass.getText().toString().trim();

        if (ToolUtils.isEmpty(userID,password,rPass)){
            rgUser.setError(getString(R.string.error_required));
            rgPass.setError(getString(R.string.error_required));
            rgRePass.setError(getString(R.string.error_required));
        }else {
            // 检查用户是否输入了有效的密码。
            if (password.length() < 4) {
                rgPass.setError(getString(R.string.error_invalid_password));
                return;
            }
            if (!password.equals(rPass)) {
                rgRePass.setError(getString(R.string.error_field));
                return;
            }
            //判断用户是否存在
            Cursor cursor = sqlHelper.querysql("user", new String[]{"userid"}, "userid=?", new String[]{userID}, null, null, null);
            if (cursor.getCount() > 0) {
                Toast.makeText(RegisterActivity.this, "账号已存在|注册失败！", Toast.LENGTH_SHORT).show();
            } else {
                dialog.showLoading();
                //传递用户账号、密码
                intent.putExtra("user", userID);
                intent.putExtra("pass", password);
                //用户账号、密码存入数据库
                sqlHelper.exec("INSERT INTO user(userid,password) VALUES('"+userID+"','"+password+"')");
                success();
            }
        }
    }

    //注册成功
    private void success() {
        dialog.dissLoading();
        setResult(REGISTETOLOGINR, intent);
        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        onBackPressed();
        sqlHelper.closeDb();
        finish();
    }

    /**
     * 控件初始化
     */
    private void init() {
        intent = new Intent();
        rgPass = findViewById(R.id.rg_pass);
        rgRePass = findViewById(R.id.rg_re_pass);
        rgUser = findViewById(R.id.rg_user);
        register = findViewById(R.id.rg_btn);
        sqlHelper = new SqlHelper(this);
        //加载对话框
        dialog = new Dissloading(this);
        dialog.setMessage("正在注册中...");
        dialog.setCancelable(false);

        toolbar = findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);
    }

}

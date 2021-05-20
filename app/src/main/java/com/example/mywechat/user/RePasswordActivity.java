package com.example.mywechat.user;

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
import com.example.mywechat.tool.SqlHelper;
import com.example.mywechat.tool.ToolUtils;


/**
 * 修改密码类
 */
public class RePasswordActivity extends AppCompatActivity {

    private ImageButton modify;
    private EditText reUser, passOld, passNew, passNew1;
    private SqlHelper sqlHelper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.repass);
        init();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        //返回false表示点击后，隐藏软键盘;返回true表示保留软键盘.
        passNew1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptRePass();
                    return false;
                }
                return true;
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRePass();
            }
        });
    }

    /**
     * 修改密码功能
     */
    private void attemptRePass() {
        // 重置错误
        reUser.setError(null);
        passOld.setError(null);
        passNew.setError(null);
        passNew1.setError(null);
        //获取输入内容
        String userID = reUser.getText().toString().trim();
        String passwordOld = passOld.getText().toString().trim();
        String passwordNew = passNew.getText().toString().trim();
        String passwordNew1 = passNew1.getText().toString().trim();

        if (ToolUtils.isEmpty(userID,passwordOld,passwordNew,passwordNew1)){
            reUser.setError(getString(R.string.error_required));
            passOld.setError(getString(R.string.error_required));
            passNew.setError(getString(R.string.error_required));
            passNew1.setError(getString(R.string.error_required));
        }else {
            if (!passwordNew.equals(passwordNew1)) {
                passNew1.setError(getString(R.string.error_field));
                return;
            }
            //判断用户是否存在
            Cursor cursor = sqlHelper.querysql("user", new String[]{"userid"}, "userid=?", new String[]{userID}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor = sqlHelper.querysql("user", new String[]{"password"}, "userid=?", new String[]{userID}, null, null, null);
                if (cursor.moveToFirst()) {
                    cursor.move(0);
                }
                //判断密码是否相同
                if (passwordOld.equals(cursor.getString(0))) {
                    //更新数据库密码
                    sqlHelper.exec("update user set password = " + passwordNew + " where userid ='" + userID+"'");
                    success();
                } else {
                    Toast.makeText(RePasswordActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RePasswordActivity.this, "账号不存在！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 修改成功
     */
    private void success() {
        Toast.makeText(RePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
        onBackPressed();
        sqlHelper.closeDb();
        finish();
    }

    /**
     * 控件初始化
     */
    private void init() {
        passOld = findViewById(R.id.re_pass_old);
        passNew = findViewById(R.id.re_pass_new);
        passNew1 = findViewById(R.id.re_pass_new1);
        reUser = findViewById(R.id.re_user);
        modify = findViewById(R.id.re_btn);
        sqlHelper = new SqlHelper(this);

        toolbar = findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);
    }
}

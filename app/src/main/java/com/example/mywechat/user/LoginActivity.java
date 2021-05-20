package com.example.mywechat.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.example.mywechat.MainActivity;
import com.example.mywechat.R;
import com.example.mywechat.tool.Dissloading;
import com.example.mywechat.tool.SqlHelper;
import com.example.mywechat.tool.ToolUtils;

import java.lang.reflect.Method;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity {

    //UI的引用
    private EditText mPassword, mUser;
    private ImageButton signInButton;
    private Toolbar toolbar;
    private Dissloading dialog;
    private SqlHelper sqlHelper;
    int LOGINTOREGISTER = 10001;
    int REGISTETOLOGINR = 10002;
    private long mBackPressed;
    private static final int TIME_INTERVAL = 2000;
    private CheckBox rememberPw, autoLogin;
    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化
        init();

        loadSP();
        //返回false表示点击后，隐藏软键盘;返回true表示保留软键盘.
        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return false;
                }
                return true;
            }
        });

        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (autoLogin.isChecked()) {
                    rememberPw.setChecked(true);
                }
                ed.putBoolean("AUTO_LOGIN", autoLogin.isChecked());
                ed.commit();
            }
        });

        rememberPw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ed.putBoolean("REMEMBER_PW", rememberPw.isChecked());
                ed.commit();
            }
        });
    }

    private void loadSP() {
        sp = getSharedPreferences("userInfo", 0);
        ed = sp.edit();
        boolean remember_Pw = sp.getBoolean("REMEMBER_PW", false);
        boolean auto_Login = sp.getBoolean("AUTO_LOGIN", false);
        rememberPw.setChecked(remember_Pw);
        autoLogin.setChecked(auto_Login);
        String names = sp.getString("USER_NAME", "");
        String passwords = sp.getString("PASSWORD", "");
        if (remember_Pw) {
            mUser.setText(names);
            mPassword.setText(passwords);
        }
        Intent intent = getIntent();
        if (intent.getStringExtra("code") != null) {
            if (intent.getStringExtra("code").equals("relogin")) {
                auto_Login = false;
            }
        }
        if (auto_Login) {
            attemptLogin();
        }
    }


    /**
     * 控件初始化
     */
    private void init() {
        toolbar = findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);

        mUser = (EditText) findViewById(R.id.user);
        mPassword = (EditText) findViewById(R.id.password);
        signInButton = (ImageButton) findViewById(R.id.sign_in_button);
        dialog = new Dissloading(this);
        dialog.setMessage("正在登录中...");
        dialog.setCancelable(false);
        sqlHelper = new SqlHelper(this);

        rememberPw = (CheckBox) findViewById(R.id.remember_pw);
        autoLogin = (CheckBox) findViewById(R.id.auto_login);
    }

    /**
     * 登录功能
     */
    private void attemptLogin() {
        // 重置错误
        mUser.setError(null);
        mPassword.setError(null);
        // 存储登录
        String user = mUser.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if (ToolUtils.isEmpty(user, password)) {
            mUser.setError(getString(R.string.error_required));
            mPassword.setError(getString(R.string.error_required));
        } else {
            // 检查用户是否输入了有效的密码。
            if (password.length() < 4) {
                mPassword.setError(getString(R.string.error_invalid_password));
                return;
            }
            Cursor cursor = sqlHelper.querysql("user", new String[]{"userid"}, "userid=?", new String[]{user}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor = sqlHelper.querysql("user", new String[]{"password"}, "userid=?", new String[]{user}, null, null, null);
                if (cursor.moveToFirst()) {
                    cursor.move(0);
                }
                if (password.equals(cursor.getString(0))) {
                    //显示加载框
                    dialog.showLoading();
                    dialog.dissLoading();
                    //跳转到智慧厨房界面
                    Intent intent = new Intent(this, MainActivity.class);
                    Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
                    intent.putExtra("user", user);

                    if (rememberPw.isChecked()) {
                        ed.putString("USER_NAME", user);
                        ed.putString("PASSWORD", password);
                        ed.putBoolean("REMEMBER_PW", true);
                        ed.commit();
                    }
                    if (autoLogin.isChecked()) {
                        ed.putBoolean("AUTO_LOGIN", true);
                        ed.commit();
                    }
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "密码错误|登录失败！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "账号不存在|登录失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 选择菜单方法创建
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //设置overflow图标
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.mipmap.acircle));
        //加载选择菜单布局文件
        getMenuInflater().inflate(R.menu.menu_select, menu);
        return true;
    }

    /**
     * 选择菜单实现交互功能
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reg:
                // 重置错误
                mUser.setError(null);
                mPassword.setError(null);
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, LOGINTOREGISTER);
                break;
            case R.id.res:
                //重置错误
                mUser.setError(null);
                mPassword.setError(null);
                Intent intent1 = new Intent(this, RePasswordActivity.class);
                startActivity(intent1);
                mPassword.setText(null);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 让菜单同时显示图标和文字
     *
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * 注册返回用户账号和密码
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGINTOREGISTER) {
            if (resultCode == REGISTETOLOGINR) {
                if (data != null) {
                    mUser.setText(data.getStringExtra("user"));
                    mPassword.setText(data.getStringExtra("pass"));
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            System.exit(0);
            return;
        } else {
            Toast.makeText(getBaseContext(), "再按一次返回退出程序", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    /**
     * 关闭资源
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }
}


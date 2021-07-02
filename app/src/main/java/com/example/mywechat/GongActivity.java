package com.example.mywechat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import com.example.mywechat.fragment.GongAdapter;

/**
 * 公众号界面类
 * @author sen
 */
public class GongActivity extends AppCompatActivity {

    //操作栏
    private Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gongzong);
        init();

        GongAdapter listAdapter = new GongAdapter(this);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),TengActivity.class);
                startActivity(i);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gong_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sh:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint("搜索");

                searchView.setOnSearchClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //当点击搜索编辑框的时候执行，刚进入时默认点击搜索编辑框
                    }
                });

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        //点击软键盘搜索的时候执行
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        //搜索框文本发生改变的时候执行
                        return false;
                    }
                });
                searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        //当得到焦点和失去焦点的时候执行
                    }
                });

                item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        //展开时回调
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        //收起来时回调
                        return true;
                    }
                });
                break;
            default:
                break;
        }
        return true;
    }

    private void init() {
        toolbar = findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.friends);
    }
}
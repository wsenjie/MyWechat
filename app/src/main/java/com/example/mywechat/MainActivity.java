package com.example.mywechat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.mywechat.fragment.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 主界面 仿微信界面
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationBar bottomNavigationBar;
    private Toolbar toolbar;
    private ViewPager viewPager;
    //用于存放创建的fragment
    private List<Fragment> fragmentList = new ArrayList<>();
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        /** 1.导航基础设置 包括按钮选中效果 导航栏背景色等 */
        bottomNavigationBar
                /**
                 *  setMode() 内的参数有三种模式类型：
                 *  MODE_DEFAULT 自动模式：导航栏Item的个数<=3 用 MODE_FIXED 模式，否则用 MODE_SHIFTING 模式
                 *  MODE_FIXED 固定模式：未选中的Item显示文字，无切换动画效果。
                 *  MODE_SHIFTING 切换模式：未选中的Item不显示文字，选中的显示文字，有切换动画效果。
                 */
                .setMode(BottomNavigationBar.MODE_SHIFTING)
                /**
                 *  setBackgroundStyle() 内的参数有三种样式
                 *  BACKGROUND_STYLE_DEFAULT: 默认样式 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC
                 *                                    如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
                 *  BACKGROUND_STYLE_STATIC: 静态样式 点击无波纹效果
                 *  BACKGROUND_STYLE_RIPPLE: 波纹样式 点击有波纹效果
                 */
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                //选中颜色 图标和文字
                .setActiveColor(R.color.green)
                //默认背景色
                .setBarBackgroundColor("#ECECEC");

        /** 2.添加导航按钮 */
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.weixin_pressed, R.string.weixin).setInactiveIconResource(R.drawable.weixin_normal))
                .addItem(new BottomNavigationItem(R.drawable.contact_list_pressed, R.string.com).setInactiveIconResource(R.drawable.contact_list_normal))
                .addItem(new BottomNavigationItem(R.drawable.find_pressed, R.string.find).setInactiveIconResource(R.drawable.find_normal))
                .addItem(new BottomNavigationItem(R.drawable.profile_pressed, R.string.me).setInactiveIconResource(R.drawable.profile_normal))
                .initialise();//initialise 一定要放在 所有设置的最后一项

        /**
         * 设置导航选中的事件
         */
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        title.setText(R.string.weixin);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        title.setText(R.string.com);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        title.setText(R.string.find);
                        break;
                    case 3:
                        viewPager.setCurrentItem(3);
                        title.setText(R.string.me);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void init() {
        title = findViewById(R.id.title_txt);
        viewPager = (ViewPager) findViewById(R.id.vpager);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        toolbar = findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);
        //将创建的fragment放入集合
        fragmentList.add(new ContactFragment());
        fragmentList.add(new FriendFragment());
        fragmentList.add(new SettingFragment());
        fragmentList.add(new WeiXinFragment());
        //自定义的FragmentPagerAdapter适配器，传入fragment集合
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        //为ViewPager设置适配器
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //设置overflow图标
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.mipmap.acircle));
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sh:
                Toast.makeText(MainActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fg:
                Toast.makeText(MainActivity.this, "发起群聊", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add:
                Toast.makeText(MainActivity.this, "添加朋友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.scan:
                Toast.makeText(MainActivity.this, "扫一扫", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sf:
                Toast.makeText(MainActivity.this, "收付款", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(MainActivity.this, "帮助与反馈", Toast.LENGTH_SHORT).show();
                break;
            default:
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
}

package com.example.mywechat;

import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公众号聊天界面类
 */
public class TengActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout bottomLayout;// 底部菜单父框架
    private LinearLayout bottomMenuLayout1;// 底部菜单布局
    private LinearLayout bottomMenuLayout2;// 底部输入框布局
    private RelativeLayout btn1;// “用户绑定”按钮布局
    private RelativeLayout btn2;// “扫描签到”按钮布局
    private RelativeLayout btn3;// “更多”按钮布局
    private LinearLayout popLayout1;
    private LinearLayout popLayout2;
    private LinearLayout popLayout3;// 弹出的子菜单父框架布局
    private LinearLayout childLayout;// “更多”按钮的子菜单
    private ListView lv;
    private MyAdapter adapter;
    private List<Map<String, String>> listData = new ArrayList<>();
    private ImageView keyboard;// 底部键盘切换图标
    private ImageView menu;// 底部菜单切换图标
    private ImageView title_bar_my;
    private Button send;// 发送按钮
    private EditText inputText;// 输入框
    private boolean open = true;// 子菜单填充状态标记
    private boolean flag = false;// 子菜单显示状态标记
    private boolean bind = false;// 用户绑定状态标记
    private View view;
    private View view2;
    private LayoutInflater inflater;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teng);
        inflater = TengActivity.this.getLayoutInflater();

        toolbar = findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);
        popLayout1 = (LinearLayout) findViewById(R.id.pop_layout1);
        popLayout2 = (LinearLayout) findViewById(R.id.pop_layout2);
        popLayout3 = (LinearLayout) findViewById(R.id.pop_layout3);
        bottomLayout = (LinearLayout) findViewById(R.id.bottom_layout);
        bottomMenuLayout1 = (LinearLayout) findViewById(R.id.bottom_menu_layout1);
        keyboard = (ImageView) findViewById(R.id.keyboard);

        btn1 = (RelativeLayout) findViewById(R.id.btn1);
        btn2 = (RelativeLayout) findViewById(R.id.btn2);
        btn3 = (RelativeLayout) findViewById(R.id.btn3);
        lv = (ListView) findViewById(R.id.lv);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        keyboard.setOnClickListener(this);
        adapter = new MyAdapter(this, listData);
        lv.setAdapter(adapter);
        title_bar_my = findViewById(R.id.title_bar_my);

        title_bar_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"腾讯招聘面试服务",Toast.LENGTH_SHORT).show();
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
                btn1Click();
                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
                btn3Click();
                break;
            case R.id.keyboard:
                keyboardClick();
                break;
            case R.id.menu:
                menuClick();
                break;
            case R.id.add:
                sendClick();
                break;
            default:
                break;
        }
    }

    // 用户绑定
    private void btn1Click() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "0");
        if (!bind) {
            map.put("text", "请输入您的手机号或简历ID进行帐号绑定，绑定成功后才能进行签到。");
        } else {
            map.put("text", "帐号已绑定成功，请您准时签到。");
        }
        listData.add(map);
        adapter.notifyDataSetChanged();
    }

    public void btn2Click() {// 扫描签到

    }

    /**
     * 更多
     */
    private void btn3Click() {
        if (open) {
            view = inflater.inflate(R.layout.child_menu, popLayout3, true);
            childLayout = (LinearLayout) view.findViewById(R.id.child_layout);
            open = false;
        }
        if (!flag) {
            flag = true;
            childLayout.setVisibility(View.VISIBLE);
        } else {
            flag = false;
            childLayout.setVisibility(View.GONE);
        }
    }

    //点击键盘按钮，由底部菜单切换为底部输入
    private void keyboardClick() {
        view2 = inflater.inflate(R.layout.bottom_menu_layout2, bottomLayout, true);
        bottomMenuLayout2 = (LinearLayout) view2.findViewById(R.id.bottom_menu_layout2);
        bottomMenuLayout1.setVisibility(View.GONE);
        bottomMenuLayout2.setVisibility(View.VISIBLE);
        menu = (ImageView) view2.findViewById(R.id.menu);
        inputText = (EditText) view2.findViewById(R.id.input_text);
        send = (Button) view2.findViewById(R.id.add);
        menu.setOnClickListener(this);
        send.setOnClickListener(this);
        inputClick();
    }

    //点击菜单按钮，由底部输入框切换为底部菜单
    private void menuClick() {
        bottomMenuLayout2.setVisibility(View.GONE);
        bottomMenuLayout1.setVisibility(View.VISIBLE);
    }

    /**
     * 点击输入
     */
    private void inputClick() {
        inputText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    send.setBackgroundResource(R.drawable.send);
                    send.setText("发送");
                } else {
                    send.setBackgroundResource(R.drawable.send);
                    send.setText(" ");
                }
            }
        });
    }

    /**
     * 点击发送
     */
    private void sendClick() {
        String text = inputText.getEditableText().toString();
        inputText.setText("");
        if (text != null && (!text.equals(""))) {
            Map<String, String> map;
            map = new HashMap<>();
            map.put("type", "1");// 消息类型，服务端为0，用户为1
            map.put("text", text);
            listData.add(map);
            map = new HashMap<>();
            map.put("type", "0");
            map.put("text", "帐号已绑定成功，请您准时签到。");
            listData.add(map);
            adapter.notifyDataSetChanged();
            bind = true;
        }
    }

    /**
     * 适配器
     */
    private class MyAdapter extends BaseAdapter {
        public List<Map<String, String>> list;
        private Context context;

        MyAdapter(Context context, List<Map<String, String>> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {

            return list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            Map<String, String> map = list.get(position);

            convertView = null;
            if (map.get("type").equals("0")) {// 服务端
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_left, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.mTextView = (TextView) convertView.findViewById(R.id.server_text);
                    viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.server_image);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.mTextView.setText(map.get("text"));
            } else {// 用户
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_right, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.mTextView = (TextView) convertView.findViewById(R.id.user_text);
                    viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.user_image);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.mTextView.setText(map.get("text"));
            }
            return convertView;
        }
    }

    private final class ViewHolder {
        TextView mTextView;
        ImageView mImageView;
    }
}
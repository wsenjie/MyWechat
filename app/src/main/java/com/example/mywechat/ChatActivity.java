package com.example.mywechat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天界面类
 */
public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lv;
    private MyAdapter adapter;
    private List<Map<String, String>> listData = new ArrayList<>();
    private ImageView title_bar_my;
    private ImageView send;// 发送按钮
    private EditText inputText;// 输入框
    private LayoutInflater inflater;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        inflater = ChatActivity.this.getLayoutInflater();

        inputText = (EditText) findViewById(R.id.input_text);
        send = (ImageView) findViewById(R.id.add);
        toolbar = findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.lv);
        adapter = new MyAdapter(this, listData);
        lv.setAdapter(adapter);
        title_bar_my = findViewById(R.id.title_bar_my);

        title_bar_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"小明",Toast.LENGTH_SHORT).show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        inputText.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                sendClick();
                break;
            case R.id.input_text:
                send.setImageResource(R.drawable.send);
                break;
            default:
                break;
        }
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
            map.put("type", "1");// 消息类型，用户1为0，用户2为1
            map.put("text", text);
            listData.add(map);
            adapter.notifyDataSetChanged();
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
            if (map.get("type").equals("0")) {// 用户1
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
            } else {// 用户2
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

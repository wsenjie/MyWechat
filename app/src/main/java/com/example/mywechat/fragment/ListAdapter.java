package com.example.mywechat.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mywechat.R;

/**
 * 聊天主界面适配器类
 */
public class ListAdapter extends BaseAdapter {

    Context context;

    public ListAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.chatlistitem, null);

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.time = (TextView) convertView.findViewById(R.id.hahatime);
            holder.msg = (TextView) convertView.findViewById(R.id.msg);
            holder.photo = (ImageView) convertView.findViewById(R.id.photo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;

    }
    static class ViewHolder {
        //listview参数
        public TextView name;
        public TextView time;
        public TextView msg;
        public ImageView photo;

    }
}

package com.example.mywechat.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mywechat.R;

/**
 * 通讯录列表适配器类
 * @author sen
 */
public class ListfriendsAdapter extends BaseAdapter {

    Context context;

    public ListfriendsAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 50;
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
            convertView = View.inflate(context,R.layout.chatlistfriendsitem, null);

            holder.name = (TextView) convertView.findViewById(R.id.name);
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
        public ImageView photo;

    }
}

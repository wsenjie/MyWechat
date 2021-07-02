package com.example.mywechat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.mywechat.GongActivity;
import com.example.mywechat.R;

/**
 * 通讯录类
 */
public class FriendFragment extends Fragment {
    private ListView listView;
    private View headview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friendfragment, container, false);

        listView = (ListView) view.findViewById(R.id.friends);
        headview = LayoutInflater.from(getActivity()).inflate(R.layout.friendsheadview,listView,false);
        listView.addHeaderView(headview);
        ListfriendsAdapter listAdapter = new ListfriendsAdapter(getActivity());
        listView.setAdapter(listAdapter);

        //公众号
        headview.findViewById(R.id.gong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GongActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}

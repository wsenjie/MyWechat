package com.example.mywechat.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.mywechat.MyInfoAcitvity;
import com.example.mywechat.PayActivity;
import com.example.mywechat.R;
import com.example.mywechat.user.LoginActivity;

import java.util.Objects;

/**
 * 个人中心界面类（我）
 */
public class WeiXinFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weixinfragment, container, false);

        //个人信息
        view.findViewById(R.id.rl_my).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MyInfoAcitvity.class);
                startActivity(i);
            }
        });
        //支付
        view.findViewById(R.id.pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PayActivity.class);
                startActivity(i);
            }
        });

        //关闭微信
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                builder.setTitle("提示");
                builder.setMessage("确定退出微信？");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
            }
        });

        //退出登录
        view.findViewById(R.id.out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("确定退出当前用户？");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.putExtra("code","relogin");
                        startActivity(intent);
                        Objects.requireNonNull(getActivity()).finish();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
            }
        });

        return view;
    }
}

package com.example.mywechat.tool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * 对话框类
 */
public class Dissloading {

    private ProgressDialog dialog;

    public Dissloading(Context context){
        dialog = new ProgressDialog(context);
    }

    public void setMessage(String message){
        dialog.setMessage(message);
    }

    public void setCancelable(boolean b){
        dialog.setCancelable(b);
    }

    public void dissLoading() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                Context context = ((ContextWrapper) dialog.getContext()).getBaseContext();
                if (context instanceof Activity) {
                    if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        }
    }

    /**
     * 显示加载
     */
    public void showLoading() {
        dialog.show();
    }
}

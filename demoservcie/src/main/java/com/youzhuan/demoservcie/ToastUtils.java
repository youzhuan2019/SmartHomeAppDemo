package com.youzhuan.demoservcie;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.widget.Toast;

public class ToastUtils {
    static Toast mToast;

    @SuppressLint("ShowToast")
    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getInstance(), "", Toast.LENGTH_LONG);
        }
        mToast.setText(msg);
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showToast( int id) {
        if (mToast == null) {
            try {
                mToast = Toast.makeText(App.getInstance(), "", Toast.LENGTH_SHORT);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        mToast.setText(getResources().getString(id));
        mToast.show();
    }

    public static Resources getResources() {
        return App.getInstance().getResources();
    }
}

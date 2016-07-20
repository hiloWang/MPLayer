package com.hhly.lawyer.util;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

public class ToastUtils {

    private Context context;

    @Inject
    public ToastUtils(Context context) {
        this.context = context;
    }

    public void makeText(String msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }

    public void makeText(int resId, int duration) {
        makeText(context.getResources().getString(resId), duration);
    }

    public void makeText(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void makeText(int resId) {
        Toast.makeText(context, context.getResources().getString(resId), Toast.LENGTH_SHORT).show();
    }

}

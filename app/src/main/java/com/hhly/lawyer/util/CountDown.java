package com.hhly.lawyer.util;

import android.os.CountDownTimer;

public class CountDown extends CountDownTimer {

    /**
     * 60s ， 倒计时的时候不准确,
     */
    public static final int TIMEOUT = 59699;
    public static final int TIMEOUT_INTERVEL = 1000;

    private CountDownCallback callback;

    /**
     * @param millisInFuture    总时长
     * @param countDownInterval 间隔时间
     * @param callback
     */
    public CountDown(long millisInFuture, long countDownInterval, CountDownCallback callback) {
        super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        this.callback = callback;
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        if (callback != null) {
            callback.onFinish();
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示
        if (callback != null) {
            callback.onTick(millisUntilFinished);
        }
    }

    /**
     * 获取一个时长60s,间隔1s的倒计时
     *
     * @param callback
     * @return
     */
    public static CountDown getDefault(CountDownCallback callback) {
        return new CountDown(TIMEOUT, TIMEOUT_INTERVEL, callback);
    }


    public interface CountDownCallback {
        void onFinish();

        void onTick(long millisUntilFinished);
    }
}
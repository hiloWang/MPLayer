package com.hhly.lawyer.data;

import com.hhly.lawyer.data.entity.HttpResult;

import rx.functions.Func1;

public class ResponseFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> httpResult) {
        if (httpResult.code != 200) {
            // 在此处抛出异常，subscribe的onError方法中会收到异常。
            // 抛出的异常不能是会使系统崩溃的检查异常，如OOM
            throw new IllegalStateException(httpResult.message);
        }
        return httpResult.data;
    }
}
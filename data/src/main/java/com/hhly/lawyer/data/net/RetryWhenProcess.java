package com.hhly.lawyer.data.net;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * 网络延迟中间处理类
 */
public class RetryWhenProcess implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private long mInterval;
    private int mRetryTimes = 3;

    /**
     * 通过Observable.retry(new RetryWhenProcess(<Time>))方式
     *
     * @param interval 设置延迟的时间
     */
    public RetryWhenProcess(long interval) {
        this(3, interval);
    }

    public RetryWhenProcess(int retryTimes, long interval) {
        this.mRetryTimes = retryTimes;
        this.mInterval = interval;
    }

    @Override
    public Observable<?> call(final Observable<? extends Throwable> observable) {
        return observable.flatMap(throwable -> observable.flatMap(new Func1<Throwable, Observable<?>>() {
            @Override
            public Observable<?> call(final Throwable throwable) {
                // 如何用户没有打开网络，则不进行连接，省电；
                if (throwable instanceof ConnectException) {
                    return Observable.error(throwable);
                }
                return Observable.range(1, mRetryTimes)
                        .map(integer -> throwable)
                        .flatMap((Func1<Throwable, Observable<?>>) throwable1 -> Observable.timer(mInterval, TimeUnit.MILLISECONDS));
            }
        }));
    }
}

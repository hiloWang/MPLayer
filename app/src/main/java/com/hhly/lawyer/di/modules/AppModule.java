package com.hhly.lawyer.di.modules;

import android.content.Context;

import com.hhly.lawyer.data.executor.JobExecutor;
import com.hhly.lawyer.data.executor.ThreadExecutor;
import com.hhly.lawyer.util.RxBus;
import com.hhly.lawyer.util.RxUtils;
import com.hhly.lawyer.util.ToastUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module类，管理着其他类的实例对象(Module与Provides为了解决第三方库而生)，
 * 这里的Provides起着建立桥梁的作用（与Component另一端目标类（@Inject）建立关系）;
 * 注：如果是返回值的类型接口，则形参必须是接口的实现类才可以，并在实现类的构造函数上@Inject注入依赖，如果返回值并非接口，则一般通过return new X();的方式返回该实例对象
 */
@Module
public class AppModule {

    Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    public ToastUtils provideToastUtils() {
        return new ToastUtils(context);
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    public RxUtils provideRxUtils() {
        return new RxUtils();
    }

    @Provides
    @Singleton
    public RxBus provideRxBus() {
        return new RxBus();
    }
}

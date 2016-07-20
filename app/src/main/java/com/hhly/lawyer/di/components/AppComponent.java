package com.hhly.lawyer.di.components;

import com.hhly.lawyer.data.executor.ThreadExecutor;
import com.hhly.lawyer.di.modules.AppModule;
import com.hhly.lawyer.ui.base.BaseAppCompatActivity;
import com.hhly.lawyer.util.RxBus;
import com.hhly.lawyer.util.RxUtils;
import com.hhly.lawyer.util.ToastUtils;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ApplicationComponent管理全局类的实例，生命周期与app一样长,
 * Component类必须是接口或者抽象类，起着桥梁的作用，
 * 一端连着目标类（@Inject），另一端连着Module管理的实例对象(可以有多个Mudule、用modules管理)
 * Component提供返回该实例的方法，意味着调用者只需拿到Component实例即可操纵这些全局类的实例
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(BaseAppCompatActivity baseAppCompatActivity);

    ToastUtils toast();

    RxUtils rx();

    ThreadExecutor threadExecutor();

    RxBus rxBus();
}

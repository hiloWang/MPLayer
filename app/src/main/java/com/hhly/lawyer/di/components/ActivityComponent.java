package com.hhly.lawyer.di.components;

import android.app.Activity;

import com.hhly.lawyer.di.modules.ActivityModule;
import com.hhly.lawyer.di.scope.PerActivity;

import dagger.Component;

/**
 * 管理所有Activity类的实例，生命周期与activity生命周期一样长，是所有ActivityComponent的基类，只提供返回当前Activity的实例方法
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

	//Exposed to sub-graphs.
	Activity getActivity();

}

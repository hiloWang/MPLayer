package com.hhly.lawyer.di.components;

import com.hhly.lawyer.di.modules.ActivityModule;
import com.hhly.lawyer.di.modules.MainModule;
import com.hhly.lawyer.di.scope.PerActivity;
import com.hhly.lawyer.ui.view.two.ExpenseOrdersFragment;
import com.hhly.lawyer.ui.view.one.HomeFragment;
import com.hhly.lawyer.ui.view.three.TabLayoutFragment;
import com.hhly.lawyer.ui.view.four.LoginFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, MainModule.class})
public interface MainComponent {

    void inject(HomeFragment fragment);

    void inject(ExpenseOrdersFragment fragment);

    void inject(TabLayoutFragment fragment);

    void inject(LoginFragment fragment);
}

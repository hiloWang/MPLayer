package com.hhly.lawyer.di.components;

import com.hhly.lawyer.di.modules.ActivityModule;
import com.hhly.lawyer.di.modules.MainModule;
import com.hhly.lawyer.di.scope.PerActivity;
import com.hhly.lawyer.ui.view.HomeFragment;
import com.hhly.lawyer.ui.view.Page2Fragment;
import com.hhly.lawyer.ui.view.Page3Fragment;
import com.hhly.lawyer.ui.view.LoginFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, MainModule.class})
public interface MainComponent {

    void inject(HomeFragment fragment);

    void inject(Page2Fragment fragment);

    void inject(Page3Fragment fragment);

    void inject(LoginFragment fragment);
}

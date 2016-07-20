package com.hhly.lawyer.data.di.components;

import com.hhly.lawyer.data.cache.LocalCache;
import com.hhly.lawyer.data.di.modules.ApiModule;
import com.hhly.lawyer.data.repository.DataStore;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {

    DataStore dataStore();

    LocalCache localCache();
}

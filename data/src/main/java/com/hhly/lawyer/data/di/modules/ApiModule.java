package com.hhly.lawyer.data.di.modules;

import android.content.Context;

import com.anupcowkur.reservoir.Reservoir;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hhly.lawyer.data.api.RestApi;
import com.hhly.lawyer.data.cache.LocalCache;
import com.hhly.lawyer.data.cache.LocalCacheFactory;
import com.hhly.lawyer.data.repository.DataStore;
import com.hhly.lawyer.data.repository.DataStoreRepository;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    // OkHttp Constants
    public static final String RESPONSE_CACHE_FILE = "response_cache";
    public static final long RESPONSE_CACHE_SIZE = 10 * 1024 * 1024L;
    public static final long HTTP_CONNECT_TIMEOUT = 10L;
    public static final long HTTP_READ_TIMEOUT = 30L;
    public static final long HTTP_WRITE_TIMEOUT = 10L;

    // API 地址
    public final String API_HOST_URL;
    private final Context context;

    // LocalCacheSize
    public static final long LOCAL_CACHE_SIZE = 3 * 1024 * 1024L;

    public final Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .create();


    public ApiModule(Context context, String apiHostUrl) {
        this.context = context;
        API_HOST_URL = apiHostUrl;
        initReservoir(context);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        File cacheDir = new File(context.getCacheDir(), RESPONSE_CACHE_FILE);

        return new OkHttpClient.Builder()
                .cache(new Cache(cacheDir, RESPONSE_CACHE_SIZE))
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(API_HOST_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public RestApi provideRestApi(Retrofit retrofit) {
        return retrofit.create(RestApi.class);
    }

    @Provides
    @Singleton
    public LocalCache provideLocalCache() {
        return new LocalCacheFactory();
    }

    @Provides
    @Singleton
    public DataStore provideDataStore(RestApi restApi, LocalCache localCache) {
        return new DataStoreRepository(restApi, localCache, gson);
    }

    private void initReservoir(Context context) {
        try {
            Reservoir.init(context, LOCAL_CACHE_SIZE, gson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

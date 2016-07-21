package com.hhly.lawyer.data.repository;

import com.google.gson.Gson;
import com.hhly.lawyer.data.api.RestApi;
import com.hhly.lawyer.data.cache.LocalCache;
import com.hhly.lawyer.data.entity.Wrapper;
import com.hhly.lawyer.data.exception.NetworkConnectionException;
import com.hhly.lawyer.data.util.Logger;

import java.net.UnknownServiceException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Factory that creates different implementations of {@link DataStore}.
 */
public class DataStoreRepository implements DataStore {

    private final String DUMMY_KEY = DataStoreRepository.class.getSimpleName();

    private final RestApi restApi;
    private final LocalCache localCache;
    private final Gson gson;

    public DataStoreRepository(RestApi restApi, LocalCache localCache, Gson gson) {
        this.restApi = restApi;
        this.localCache = localCache;
        this.gson = gson;
    }

    /**
     * 本地缓存
     */
    private final Action1<Object> saveDataToCache = o -> {
        if (o != null) {
            DataStoreRepository.this.localCache.refresh(DUMMY_KEY, o);
        }
    };

    private <T> Observable<T> extractData(Observable<Wrapper> observable, Class<T> clazz) {
        return observable.flatMap(wrapper -> {
            if (wrapper == null) {
                return Observable.error(new NetworkConnectionException());
            } else if (wrapper.statusCode == 200) {
                return Observable.just(gson.fromJson(gson.toJson(wrapper.data), clazz));
            } else if (wrapper.statusCode == 500) {
                return Observable.error(new UnknownServiceException());
            } else {
                if (wrapper.message != null) Logger.e(wrapper.message.toString());
                return Observable.error(new Exception(wrapper.message));
            }
        });
    }

    @Override
    public Observable<Wrapper> getDummyData(String phone, String operateType) {
        return this.extractData(restApi.getDummyData(phone, operateType), Wrapper.class)
                .doOnNext(saveDataToCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

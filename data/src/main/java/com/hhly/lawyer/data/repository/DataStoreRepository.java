package com.hhly.lawyer.data.repository;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hhly.lawyer.data.api.RestApi;
import com.hhly.lawyer.data.cache.LocalCache;
import com.hhly.lawyer.data.entity.HttpResult;
import com.hhly.lawyer.data.entity.UploadFile;
import com.hhly.lawyer.data.exception.NetworkConnectionException;

import java.io.File;
import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Factory that creates different implementations of {@link DataStore}.
 */
public class DataStoreRepository implements DataStore {

    private final String DUMMY_KEY = DataStoreRepository.class.getSimpleName();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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

    private <T> Observable<T> extractData(Observable<HttpResult> observable, Class<T> clazz) {
        return observable.flatMap(wrapper -> {
            if (wrapper == null) {
                return Observable.error(new NetworkConnectionException());
            } else if (wrapper.code == 200) {
                return Observable.just(gson.fromJson(gson.toJson(wrapper.data), clazz));
            } else if (wrapper.code == 500) {
                return Observable.error(new UnknownServiceException());
            } else {
                return Observable.error(new Exception(wrapper.message));
            }
        });
    }

    @Override
    public Observable<HttpResult<UploadFile>> uploadFile(File file) {
        return restApi.uploadFile(RequestBody.create(MediaType.parse("image/*"), file))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpResult<UploadFile>> uploadFiles(File... files) {
        Map<String, RequestBody> partMap = new HashMap<>();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            partMap.put("file\";filename=\"" + file.getName() + "\"", requestBody);
        }
        return restApi.uploadFiles(partMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpResult<UploadFile>> uploadFileAndField(File file, String fieldName) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody fieldNameBody = RequestBody.create(MediaType.parse("text/plain"), fieldName);
        return restApi.uploadFileAndField(requestBody, fieldNameBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseBody> downloadFileGet(String url) {
        return restApi.downloadFile(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpResult> getDummyData(String phone, String operateType) {
        return this.extractData(restApi.getDummyData(phone, operateType), HttpResult.class)
                .doOnNext(saveDataToCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HttpResult> postLogin(String userName, String password) {
        if (TextUtils.isEmpty(userName)) return Observable.error(new Exception("用户名不能为空"));
        if (TextUtils.isEmpty(password)) return Observable.error(new Exception("密码不能为空"));
        return Observable.just(new HttpResult());
    }
}

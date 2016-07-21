package com.hhly.lawyer.data.repository;

import com.hhly.lawyer.data.entity.Wrapper;

import rx.Observable;

/**
 * {@link DataStoreRepository}.
 */
public interface DataStore {

    Observable<Wrapper> getDummyData(String phone, String operateType);

    Observable<Wrapper> postLogin(String userName, String password);

}

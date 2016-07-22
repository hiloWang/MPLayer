package com.hhly.lawyer.data.repository;

import com.hhly.lawyer.data.entity.HttpResult;
import com.hhly.lawyer.data.entity.UploadFile;

import java.io.File;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * {@link DataStoreRepository}.
 */
public interface DataStore {

    Observable<HttpResult<UploadFile>> uploadFile(File file);

    Observable<HttpResult<UploadFile>> uploadFiles(File... files);

    Observable<HttpResult<UploadFile>> uploadFileAndField(File file, String fieldName);

    Observable<ResponseBody> downloadFileGet(String url);

    Observable<HttpResult> getDummyData(String phone, String operateType);

    Observable<HttpResult> postLogin(String userName, String password);

}

package com.hhly.lawyer.data.api;

import com.hhly.lawyer.data.entity.HttpResult;
import com.hhly.lawyer.data.entity.UploadFile;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {

    // 文件上传：
    // 此处@Part(“file\”; filename=\”avatar.png\”“)注释的含义是该RequestBody 的名称为file，上传的文件名称为avatar.png。
    // @Part注解中的filename与上传文件的真实名称可以不匹配。

    @Multipart
    @POST("/file")
    Observable<HttpResult<UploadFile>> uploadFile(@Part("file\"; filename=\"avatar.png\"") RequestBody file);

    @Multipart
    @POST("/files")
    Observable<HttpResult<UploadFile>> uploadFiles(@PartMap Map<String, RequestBody> mapParams);

    @POST("/file")
    @Multipart
    Observable<HttpResult<UploadFile>> uploadFileAndField(@Part("file\"; filename=\"avatar.png\"") RequestBody file, @Part("nickName") RequestBody nickName);

    // 文件下载

    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);


    // 以下是各个界面自定义Api

    @GET("userCenter/sendSmsCode")
    Observable<HttpResult> getDummyData(@Query("phone") String phone,
                                        @Query("operateType") String operateType);

/*
    @GET("gameAccount/findGameAccountUsableSum")
    Observable<BaseBean<String>> getUserBalance(@Query("loginToken") String loginToken,
                                                @Query("deviceToken") String deviceToken);

    *//**
     * 获取用户信息
     * @param loginToken
     * @param deviceToken
     * @return
     *//*
    @FormUrlEncoded
    @POST("gameUser/findGameUserInfo")
    Observable<BaseBean<User>> findGameUserInfo(@Field("loginToken") String loginToken,
                                                @Field("deviceToken") String deviceToken);*/
}

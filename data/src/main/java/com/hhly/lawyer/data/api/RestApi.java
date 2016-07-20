package com.hhly.lawyer.data.api;

import com.hhly.lawyer.data.entity.Wrapper;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {

   @GET("userCenter/sendSmsCode")
   Observable<Wrapper> getDummyData(@Query("phone") String phone,
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

package com.achers.mvpdemo.interfaces;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Achers on 2017/12/9.
 */

public interface GankService {
//    @FormUrlEncoded
//    @POST("/score_request/")
//    Observable<String> getGanHuo(@Field("id") String id);

    @GET("/score_request")
    Observable<String> getGanHuo(@Query("id") String id);
}

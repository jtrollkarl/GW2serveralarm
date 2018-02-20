package com.moducode.gw2serveralarm.retrofit;

import com.moducode.gw2serveralarm.data.ServerModel;

import java.util.List;

import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Jay on 2017-08-20.
 */

public interface ServerService {

    @GET("worlds?ids=all")
    Observable<List<ServerModel>> listServers();

    @GET("worlds")
    Observable<ServerModel> getServer(@Query("id") String id);


}

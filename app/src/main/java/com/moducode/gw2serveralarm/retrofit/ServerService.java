package com.moducode.gw2serveralarm.retrofit;

import com.moducode.gw2serveralarm.data.ServerModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Jay on 2017-08-20.
 */

public interface ServerService {

    @GET("worlds")
    Single<List<ServerModel>> listServers(@Query("ids") String id);

}

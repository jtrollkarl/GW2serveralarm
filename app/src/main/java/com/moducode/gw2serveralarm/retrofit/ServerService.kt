package com.moducode.gw2serveralarm.retrofit

import com.moducode.gw2serveralarm.data.ServerModel

import io.reactivex.Observable

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jay on 2017-08-20.
 */

interface ServerService {

    @GET("worlds?ids=all")
    fun listServers(): Observable<List<ServerModel>>

    @GET("worlds")
    fun getServer(@Query("id") id: String): Observable<ServerModel>
    
}

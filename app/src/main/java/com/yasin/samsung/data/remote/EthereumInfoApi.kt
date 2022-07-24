package com.yasin.samsung.data.remote

import com.yasin.samsung.data.remote.dto.BalanceDto
import com.yasin.samsung.data.remote.dto.StatementDto
import retrofit2.http.GET
import retrofit2.http.Query

interface EthereumInfoApi {

    @GET("api")
    suspend fun getBalance(@Query("address") address: String,
                           @Query("apikey") apiKey: String,
                           @Query("module") module: String,
                           @Query("action") action: String,
                           @Query("tag") tag: String): BalanceDto

    @GET("api")
    suspend fun getTransaction(@Query("module") module:String,
                               @Query("action") action:String,
                               @Query("address") address:String,
                               @Query("startblock") startblock:Int,
                               @Query("endblock") endblock:Int,
                               @Query("page") page:String,
                               @Query("offset") offset:String,
                               @Query("sort") sort:String,
                               @Query("apikey") apikey:String): StatementDto

}
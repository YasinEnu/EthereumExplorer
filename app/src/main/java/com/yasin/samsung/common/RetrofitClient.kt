package com.yasin.samsung.common

import com.google.gson.GsonBuilder
import com.yasin.samsung.BuildConfig
import com.yasin.samsung.common.Constants.BASE_URL
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {
    private var retrofit: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()
    val client: Retrofit? get() {
            if (retrofit == null) {
                synchronized(Retrofit::class.java) {
                    if (retrofit == null) {
                        val httpClient = OkHttpClient.Builder()
                        httpClient.callTimeout(90, TimeUnit.SECONDS)
                        httpClient.connectTimeout(90, TimeUnit.SECONDS)
                        httpClient.readTimeout(90, TimeUnit.SECONDS)
                        httpClient.writeTimeout(90, TimeUnit.SECONDS)
                        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        val client = httpClient.build()
                        retrofit = Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build()
                    }
                }
            }
            return retrofit
        }
}

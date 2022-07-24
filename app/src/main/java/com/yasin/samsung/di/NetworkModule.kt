package com.yasin.samsung.di

import com.yasin.samsung.common.RetrofitClient
import com.yasin.samsung.data.remote.EthereumInfoApi
import com.yasin.samsung.data.repository.EthereumInfoRepositoryImpl
import com.yasin.samsung.domain.repository.EthereumInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideBalance(): EthereumInfoApi {
        return RetrofitClient.client!!.create(EthereumInfoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBalanceRepository(api: EthereumInfoApi): EthereumInfoRepository {
        return EthereumInfoRepositoryImpl(api)
    }

}
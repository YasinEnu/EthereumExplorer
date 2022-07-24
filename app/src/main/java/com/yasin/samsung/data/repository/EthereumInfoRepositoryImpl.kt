package com.yasin.samsung.data.repository

import com.yasin.samsung.data.remote.EthereumInfoApi
import com.yasin.samsung.data.remote.dto.BalanceDto
import com.yasin.samsung.data.remote.dto.StatementDto
import com.yasin.samsung.domain.repository.EthereumInfoRepository
import javax.inject.Inject

class EthereumInfoRepositoryImpl @Inject constructor(
    private val api: EthereumInfoApi
):EthereumInfoRepository {
    override suspend fun getBalance(address:String,apiKey:String): BalanceDto {
        return api.getBalance(address,apiKey,"account","balance","latest")
    }

    override suspend fun getTransaction(address: String, apikey: String): StatementDto {
        return api.getTransaction("account","txlist",address,0,99999999,"1","10","asc",apikey)
    }
}
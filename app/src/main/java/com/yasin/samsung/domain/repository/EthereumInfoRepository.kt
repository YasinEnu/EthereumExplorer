package com.yasin.samsung.domain.repository

import com.yasin.samsung.data.remote.dto.BalanceDto
import com.yasin.samsung.data.remote.dto.StatementDto

interface EthereumInfoRepository {

    suspend fun getBalance(address:String,apiKey:String): BalanceDto

    suspend fun getTransaction(address:String,
                               apikey:String): StatementDto

}
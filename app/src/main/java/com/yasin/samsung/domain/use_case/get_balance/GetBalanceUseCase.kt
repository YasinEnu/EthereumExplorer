package com.yasin.samsung.domain.use_case.get_balance

import com.yasin.samsung.common.Resource
import com.yasin.samsung.data.remote.dto.BalanceDto
import com.yasin.samsung.domain.repository.EthereumInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val repository:EthereumInfoRepository
) {
    operator fun invoke(address:String , apiKey:String): Flow<Resource<BalanceDto>> = flow {
        try {
            emit(Resource.Loading())
            val balance = repository.getBalance(address,apiKey)
            emit(Resource.Success(balance))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Server error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Check internet connection."))
        }
    }

}
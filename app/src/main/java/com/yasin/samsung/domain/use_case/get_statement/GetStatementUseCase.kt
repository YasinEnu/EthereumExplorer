package com.yasin.samsung.domain.use_case.get_statement

import com.yasin.samsung.common.Resource
import com.yasin.samsung.data.remote.dto.StatementDto
import com.yasin.samsung.domain.repository.EthereumInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetStatementUseCase @Inject constructor(
    private val repository:EthereumInfoRepository
) {
    operator fun invoke(address:String,apiKey:String): Flow<Resource<StatementDto>> = flow {
        try {
            emit(Resource.Loading())
            val transactions = repository.getTransaction(address,apiKey)
            emit(Resource.Success(transactions))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Server error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Check internet connection."))
        }
    }

}
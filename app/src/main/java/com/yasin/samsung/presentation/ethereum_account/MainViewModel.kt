package com.yasin.samsung.presentation.ethereum_account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasin.samsung.BuildConfig
import com.yasin.samsung.common.Resource
import com.yasin.samsung.data.remote.dto.BalanceDto
import com.yasin.samsung.data.remote.dto.ResultItem
import com.yasin.samsung.domain.use_case.get_balance.GetBalanceUseCase
import com.yasin.samsung.domain.use_case.get_statement.GetStatementUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getStatementUseCase: GetStatementUseCase
):ViewModel() {

    val progressBarLiveData = MutableLiveData<Boolean>()
    val getBalanceSuccess = MutableLiveData<BalanceDto>()
    val getStatementSuccess = MutableLiveData<List<ResultItem?>>()
    val getBalanceFailed = MutableLiveData<String>()

    fun getBalance(address:String) {
        getBalanceUseCase(address,BuildConfig.API_KEY).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    getBalanceSuccess.postValue(result.data)
                    progressBarLiveData.postValue(false)
                }
                is Resource.Error -> {
                    getBalanceFailed.postValue(result.message ?: "Unexpected error occurred")
                    progressBarLiveData.postValue(false)
                }
                is Resource.Loading -> {
                    progressBarLiveData.postValue(true)
                }
            }
        }.launchIn(viewModelScope)
    }
    fun getStatement(address:String) {
        getStatementUseCase(address,BuildConfig.API_KEY).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    getStatementSuccess.postValue(result.data!!.result ?: emptyList())
                    progressBarLiveData.postValue(false)
                }
                is Resource.Error -> {
                    getBalanceFailed.postValue(result.message ?: "Unexpected error occurred")
                    progressBarLiveData.postValue(false)
                }
                is Resource.Loading -> {
                    progressBarLiveData.postValue(true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
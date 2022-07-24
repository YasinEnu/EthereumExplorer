package com.yasin.samsung.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BalanceDto(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
){
	override fun toString(): String {
		return "BalanceDto(result=$result, message=$message, status=$status)"
	}
}

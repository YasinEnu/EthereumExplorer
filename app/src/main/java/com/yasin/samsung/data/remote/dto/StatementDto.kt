package com.yasin.samsung.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class StatementDto(

	@field:SerializedName("result")
	val result: List<ResultItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ResultItem(

	@field:SerializedName("blockHash")
	val blockHash: String? = null,

	@field:SerializedName("functionName")
	val functionName: String? = null,

	@field:SerializedName("contractAddress")
	val contractAddress: String? = null,

	@field:SerializedName("methodId")
	val methodId: String? = null,

	@field:SerializedName("transactionIndex")
	val transactionIndex: String? = null,

	@field:SerializedName("confirmations")
	val confirmations: String? = null,

	@field:SerializedName("nonce")
	val nonce: String? = null,

	@field:SerializedName("timeStamp")
	val timeStamp: String? = null,

	@field:SerializedName("input")
	val input: String? = null,

	@field:SerializedName("gasUsed")
	val gasUsed: String? = null,

	@field:SerializedName("isError")
	val isError: String? = null,

	@field:SerializedName("txreceipt_status")
	val txreceiptStatus: String? = null,

	@field:SerializedName("blockNumber")
	val blockNumber: String? = null,

	@field:SerializedName("gas")
	val gas: String? = null,

	@field:SerializedName("cumulativeGasUsed")
	val cumulativeGasUsed: String? = null,

	@field:SerializedName("from")
	val from: String? = null,

	@field:SerializedName("to")
	val to: String? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("hash")
	val hash: String? = null,

	@field:SerializedName("gasPrice")
	val gasPrice: String? = null
){
	fun getDate(): String? {
		val simpleDateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a")
		val calendar = Calendar.getInstance()
		calendar.timeInMillis = timeStamp!!.toLong()
		return simpleDateFormat.format(calendar.time)
	}
}

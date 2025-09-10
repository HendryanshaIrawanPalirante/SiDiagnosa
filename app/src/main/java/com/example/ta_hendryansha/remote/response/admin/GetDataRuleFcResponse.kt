package com.example.ta_hendryansha.remote.response.admin

import com.google.gson.annotations.SerializedName

data class GetDataRuleFcResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: List<DataItemRuleFc>,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataItemRuleFc(

	@field:SerializedName("id")
	val idRule: Int,

	@field:SerializedName("id_gejala")
	val idKeputusan: String,

	@field:SerializedName("ya")
	val ya: String,

	@field:SerializedName("tidak")
	val tidak: String
)

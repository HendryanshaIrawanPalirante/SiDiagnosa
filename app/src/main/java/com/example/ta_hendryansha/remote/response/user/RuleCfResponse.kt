package com.example.ta_hendryansha.remote.response.user

import com.google.gson.annotations.SerializedName

data class RuleCfResponse(

    @field:SerializedName("pesan")
	val pesan: String,

    @field:SerializedName("data")
	val data: ArrayList<DataItemRule>,

    @field:SerializedName("error")
	val error: Boolean
)

data class DataItemRule(

	@field:SerializedName("kode_gejala")
	val kodeGejala: String,

	@field:SerializedName("nilai_cf")
	val nilaiCf: Double,

	@field:SerializedName("intensitas")
	val intensitas: String,

	@field:SerializedName("waktu")
	val waktu: String
)

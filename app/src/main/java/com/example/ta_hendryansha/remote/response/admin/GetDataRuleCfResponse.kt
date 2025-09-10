package com.example.ta_hendryansha.remote.response.admin

import com.google.gson.annotations.SerializedName

data class GetDataRuleCfResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: List<DataItemRule>,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataItemRule(

	@field:SerializedName("kode_gejala")
	val kodeGejala: String,

	@field:SerializedName("id_rule")
	val idRule: Int,

	@field:SerializedName("nilai_cf")
	val nilaiCf: Double,

	@field:SerializedName("kode_penyakit")
	val kodePenyakit: String,

	@field:SerializedName("intensitas")
	val intensitas: String,

	@field:SerializedName("waktu")
	val waktu: String
)

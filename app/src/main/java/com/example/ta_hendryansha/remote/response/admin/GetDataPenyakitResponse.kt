package com.example.ta_hendryansha.remote.response.admin

import com.google.gson.annotations.SerializedName

data class GetDataPenyakitResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: List<DataItemPenyakit>,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataItemPenyakit(

	@field:SerializedName("nama_penyakit")
	val namaPenyakit: String,

	@field:SerializedName("kode_penyakit")
	val kodePenyakit: String
)

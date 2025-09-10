package com.example.ta_hendryansha.remote.response.admin

import com.google.gson.annotations.SerializedName

data class GetDataGejalaResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: ArrayList<DataItemGejala>,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataItemGejala(

	@field:SerializedName("kode_gejala")
	val kodeGejala: String,

	@field:SerializedName("nama_gejala")
	val namaGejala: String
)

package com.example.ta_hendryansha.remote.response.konsultasi

import com.google.gson.annotations.SerializedName

data class DetailGejalaResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: List<DataDetailItem>,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataDetailItem(

	@field:SerializedName("nama_gejala")
	val namaGejala: String,

	@field:SerializedName("id_detail")
	val idDetail: Int,

	@field:SerializedName("intensitas")
	val intensitas: String,

	@field:SerializedName("waktu")
	val waktu: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String
)

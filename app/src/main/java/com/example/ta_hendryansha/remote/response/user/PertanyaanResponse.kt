package com.example.ta_hendryansha.remote.response.user

import com.google.gson.annotations.SerializedName

data class PertanyaanResponse(

    @field:SerializedName("pesan")
	val pesan: String,

    @field:SerializedName("data")
	val data: DataPertanyaan,

    @field:SerializedName("error")
	val error: Boolean
)

data class DataPertanyaan(

	@field:SerializedName("kode_gejala")
	val kodeGejala: String,

	@field:SerializedName("nama_gejala")
	val namaGejala: String
)

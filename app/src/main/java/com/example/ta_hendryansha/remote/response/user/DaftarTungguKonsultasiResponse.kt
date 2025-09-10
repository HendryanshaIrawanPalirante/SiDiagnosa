package com.example.ta_hendryansha.remote.response.user

import com.google.gson.annotations.SerializedName

data class DaftarTungguKonsultasiResponse(

    @field:SerializedName("pesan")
	val pesan: String,

    @field:SerializedName("data")
	val data: ArrayList<DataItemKonsultasi>,

    @field:SerializedName("error")
	val error: Boolean
)

data class DataItemKonsultasi(

	@field:SerializedName("nama_pasien")
	val namaPasien: String,

	@field:SerializedName("status_konsultasi")
	val statusKonsultasi: String,

	@field:SerializedName("umur")
	val umur: Int,

	@field:SerializedName("no_bpjs")
	val noBpjs: Int,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String
)

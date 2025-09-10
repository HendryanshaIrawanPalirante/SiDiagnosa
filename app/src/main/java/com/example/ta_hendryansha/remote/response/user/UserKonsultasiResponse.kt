package com.example.ta_hendryansha.remote.response.user

import com.google.gson.annotations.SerializedName

data class UserKonsultasiResponse(

    @field:SerializedName("pesan")
	val pesan: String,

    @field:SerializedName("data")
	val data: DataUser,

    @field:SerializedName("error")
	val error: Boolean
)

data class DataUser(

	@field:SerializedName("umur")
	val umur: Int,

	@field:SerializedName("no_bpjs")
	val noBpjs: Int,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("no_kartu_berobat")
	val noKartuBerobat: String,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String
)

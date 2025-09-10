package com.example.ta_hendryansha.remote.response.admin

import com.google.gson.annotations.SerializedName

data class GetAdminResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: DataAdmin,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataAdmin(

	@field:SerializedName("nik")
	val nik: Int,

	@field:SerializedName("tipe_akun")
	val tipeAkun: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("umur")
	val umur: Int,

	@field:SerializedName("pekerjaan")
	val pekerjaan: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String,

	@field:SerializedName("email")
	val email: String
)

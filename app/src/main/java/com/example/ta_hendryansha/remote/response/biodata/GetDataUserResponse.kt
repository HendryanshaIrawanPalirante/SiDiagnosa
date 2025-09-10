package com.example.ta_hendryansha.remote.response.biodata

import com.google.gson.annotations.SerializedName

data class GetDataUserResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: List<DataItemUser>,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataItemUser(

	@field:SerializedName("tipe_akun")
	val tipeAkun: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("umur")
	val umur: Int,

	@field:SerializedName("no_bpjs")
	val noBpjs: Int,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("no_kartu_berobat")
	val noKartuBerobat: String,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String,

	@field:SerializedName("email")
	val email: String
)

package com.example.ta_hendryansha.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean
)

data class Data(

	@field:SerializedName("tipe_akun")
	val tipeAkun: String,

	@field:SerializedName("email")
	val email: String
)

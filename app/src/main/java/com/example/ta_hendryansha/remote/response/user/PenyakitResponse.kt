package com.example.ta_hendryansha.remote.response.user

import com.google.gson.annotations.SerializedName

data class PenyakitResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: String,

	@field:SerializedName("error")
	val error: Boolean
)

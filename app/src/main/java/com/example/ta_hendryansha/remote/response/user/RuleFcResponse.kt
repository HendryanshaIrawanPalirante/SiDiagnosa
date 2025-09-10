package com.example.ta_hendryansha.remote.response.user

import com.google.gson.annotations.SerializedName

data class RuleFcResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("keputusan")
	val keputusan: String,

	@field:SerializedName("error")
	val error: Boolean
)

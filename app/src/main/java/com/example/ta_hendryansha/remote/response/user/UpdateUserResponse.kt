package com.example.ta_hendryansha.remote.response.user

import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("error")
	val error: Boolean
)

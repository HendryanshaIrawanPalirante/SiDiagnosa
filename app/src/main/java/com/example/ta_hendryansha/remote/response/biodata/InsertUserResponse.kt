package com.example.ta_hendryansha.remote.response.biodata

import com.google.gson.annotations.SerializedName

data class InsertUserResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("error")
	val error: Boolean
)

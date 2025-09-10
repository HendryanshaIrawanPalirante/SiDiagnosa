package com.example.ta_hendryansha.remote.response.admin

import com.google.gson.annotations.SerializedName

data class InsertDataRuleFcResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("error")
	val error: Boolean
)

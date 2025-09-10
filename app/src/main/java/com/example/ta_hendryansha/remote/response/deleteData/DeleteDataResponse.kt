package com.example.ta_hendryansha.remote.response.deleteData

import com.google.gson.annotations.SerializedName

data class DeleteDataResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("error")
	val error: Boolean
)

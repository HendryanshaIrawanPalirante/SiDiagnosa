package com.example.ta_hendryansha.remote.response.konsultasi

import com.google.gson.annotations.SerializedName

data class KelolaNotifikasiResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("error")
	val error: Boolean
)

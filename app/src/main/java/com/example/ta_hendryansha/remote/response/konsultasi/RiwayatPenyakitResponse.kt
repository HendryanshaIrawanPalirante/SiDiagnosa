package com.example.ta_hendryansha.remote.response.konsultasi

import com.google.gson.annotations.SerializedName

data class RiwayatPenyakitResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: List<DataItemRiwayat>,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataItemRiwayat(

	@field:SerializedName("pengobatan")
	val pengobatan: String,

	@field:SerializedName("status_konsultasi")
	val statusKonsultasi: String,

	@field:SerializedName("umur")
	val umur: Int,

	@field:SerializedName("no_bpjs")
	val noBpjs: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("no_kartu_berobat")
	val noKartuBerobat: String,

	@field:SerializedName("waktu")
	val waktu: String,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String,

	@field:SerializedName("hasil_diagnosa")
	val hasilDiagnosa: String
)

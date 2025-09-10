package com.example.ta_hendryansha.remote.response.konsultasi

import com.google.gson.annotations.SerializedName

data class HasilKonsultasiResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: List<DataItemKonsultasi>,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataItemKonsultasi(

	@field:SerializedName("pengobatan")
	val pengobatan: String,

	@field:SerializedName("nik_dokter")
	val nikDokter: Int,

	@field:SerializedName("nama_dokter")
	val namaDokter: String,

	@field:SerializedName("nama_pasien")
	val namaPasien: String,

	@field:SerializedName("umur")
	val umur: Int,

	@field:SerializedName("no_bpjs")
	val noBpjs: Int,

	@field:SerializedName("status_konultasi")
	val statusKonultasi: String,

	@field:SerializedName("no_kartu_berobat")
	val noKartuBerobat: String,

	@field:SerializedName("waktu")
	val waktu: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String,

	@field:SerializedName("hasil_diagnosa")
	val hasilDiagnosa: String
)

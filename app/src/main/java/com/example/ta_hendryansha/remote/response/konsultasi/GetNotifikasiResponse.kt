package com.example.ta_hendryansha.remote.response.konsultasi

import com.google.gson.annotations.SerializedName

data class GetNotifikasiResponse(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("data")
	val data: List<DataItemNotifikasi>,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataItemNotifikasi(

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

	@field:SerializedName("waktu_konsultasi")
	val waktu: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String,

	@field:SerializedName("hasil_diagnosa")
	val hasilDiagnosa: String,

	@field:SerializedName("detail_konsultasi")
	val detailKonsultasi: List<DetailKonsultasiItem>,
)

data class DetailKonsultasiItem(

	@field:SerializedName("nama_gejala")
	val namaGejala: String,

	@field:SerializedName("id_detail")
	val idDetail: Int,

	@field:SerializedName("intensitas")
	val intensitas: String,

	@field:SerializedName("waktu")
	val waktu: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String
)

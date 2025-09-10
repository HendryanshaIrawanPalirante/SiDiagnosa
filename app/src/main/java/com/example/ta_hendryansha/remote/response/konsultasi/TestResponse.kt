package com.example.ta_hendryansha.remote.response.konsultasi

import com.google.gson.annotations.SerializedName

data class TestResponse(

	@field:SerializedName("pesan")
	val pesan: String? = null,

	@field:SerializedName("data")
	val data: List<DataItemNotif?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
)

data class DetailKonsultasiItemf(

	@field:SerializedName("nama_gejala")
	val namaGejala: String? = null,

	@field:SerializedName("id_detail")
	val idDetail: Int? = null,

	@field:SerializedName("intensitas")
	val intensitas: String? = null,

	@field:SerializedName("waktu")
	val waktu: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null
)

data class DataItemNotif(

	@field:SerializedName("nama_pasien")
	val namaPasien: String? = null,

	@field:SerializedName("umur")
	val umur: Int? = null,

	@field:SerializedName("no_bpjs")
	val noBpjs: Int? = null,

	@field:SerializedName("status_konultasi")
	val statusKonultasi: String? = null,

	@field:SerializedName("detail_konsultasi")
	val detailKonsultasi: List<DetailKonsultasiItemf?>? = null,

	@field:SerializedName("no_kartu_berobat")
	val noKartuBerobat: String? = null,

	@field:SerializedName("waktu")
	val waktu: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String? = null,

	@field:SerializedName("hasil_diagnosa")
	val hasilDiagnosa: String? = null
)

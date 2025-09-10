package com.example.ta_hendryansha.entity

import com.google.gson.Gson

data class DataKonsultasi(
    val no_bpjs: Int,
    val nama_pasien: String,
    val umur: Int,
    val jenis_kelamin: String,
    val no_artu_berobat: String,
    val status_konsultasi: String,
    val waktu: String,
    val list_data_detail: ArrayList<GejalaKonsultasi>
)

package com.example.ta_hendryansha.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RiwayatPenyakit(
    var id: Int = 0,
    var namaPasien: String? = null,
    var noBpjs: Int = 0,
    var jenisKelamin: String? = null,
    var umur: Int = 0,
    var hasilDiagnosa: String? = null,
    var pengobatan: String? = null,
    var statusKonsultasi: String? = null,
    var waktu: String? = null,
): Parcelable

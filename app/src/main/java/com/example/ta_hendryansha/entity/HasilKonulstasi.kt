package com.example.ta_hendryansha.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HasilKonulstasi(
    var id: Int = 0,
    var nikDokter: Int = 0,
    var namaDokter: String? = null,
    var noBpjs: Int = 0,
    var noKartuBerobat: String? = null,
    var namaPasien: String? = null,
    var hasilDiagnosa: String? = null,
    var waktu: String? = null,
    var umur: Int = 0,
    var jenisKelamin: String? = null,
    var pengobatan: String? = null,
    var statusKonsultasi: String? = null
): Parcelable

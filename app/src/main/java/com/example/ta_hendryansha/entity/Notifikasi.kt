package com.example.ta_hendryansha.entity

import android.os.Parcelable
import com.example.ta_hendryansha.remote.response.konsultasi.DataItemKonsultasi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notifikasi(
    var id: Int = 0,
    var noBpjs: Int = 0,
    var noKartuBerobat: String? = null,
    var namaPasien: String? = null,
    var hasilDiagnosa: String? = null,
    var umur: Int = 0,
    var jenisKelamin: String? = null,
    var statusKonsultasi: String? = null,
    var waktu: String? = null,
): Parcelable
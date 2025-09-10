package com.example.ta_hendryansha.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Admin(
    var email: String? = null,
    var password: String? = null,
    var namaLengkap: String? = null,
    var umur: Int = 0,
    var jenisKelamin: String? = null,
    var nik: Int = 0,
    var pekerjaan: String? = null,
    var tipeAkun: String? = null
): Parcelable

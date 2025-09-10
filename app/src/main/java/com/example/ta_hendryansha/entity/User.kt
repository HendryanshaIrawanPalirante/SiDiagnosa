package com.example.ta_hendryansha.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var email: String? = null,
    var password: String? = null,
    var namaLengkap: String? = null,
    var umur: Int = 0,
    var jenisKelamin: String? = null,
    var noKartuBerobat: String? = null,
    var noBpjs: Int = 0
): Parcelable

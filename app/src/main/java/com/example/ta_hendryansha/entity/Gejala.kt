package com.example.ta_hendryansha.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gejala(
    var namaGejala: String? = null,
    var kodeGejala: String? = null
): Parcelable

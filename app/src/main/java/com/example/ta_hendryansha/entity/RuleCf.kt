package com.example.ta_hendryansha.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RuleCf(
    var id: Int = 0,
    var kodePenyakit: String? = null,
    var kodeGejala: String? = null,
    var nilaiCf: Double = 0.0,
    var intensitas: String? = null,
    var waktu: String? = null
): Parcelable

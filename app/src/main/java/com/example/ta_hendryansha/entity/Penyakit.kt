package com.example.ta_hendryansha.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Penyakit(
    var namaPenyakit: String? = null,
    var kodePenyakit: String? = null
): Parcelable

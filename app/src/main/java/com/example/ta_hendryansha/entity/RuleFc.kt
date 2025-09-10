package com.example.ta_hendryansha.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RuleFc(
    var id: Int = 0,
    var idGejala: String? = null,
    var ya: String? = null,
    var tidak: String? = null
): Parcelable
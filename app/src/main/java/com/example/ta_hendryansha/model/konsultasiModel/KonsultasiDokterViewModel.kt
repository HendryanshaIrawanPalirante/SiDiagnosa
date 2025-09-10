package com.example.ta_hendryansha.model.konsultasiModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository
import com.example.ta_hendryansha.entity.GejalaKonsultasi

class KonsultasiDokterViewModel(val repository: UserRepository): ViewModel() {

    fun konsultasiDokter(
        noBpjs: Int,
        namaPasien: String,
        umur: Int,
        jenisKelamin: String,
        noKartuBerobat: String,
        hasilDiagnosa: String,
        statusKonsultasi: String,
        waktu: String,
        dataDetail: String
    ) = repository.konsultasiDokter(
        noBpjs,
        namaPasien,
        umur,
        jenisKelamin,
        noKartuBerobat,
        hasilDiagnosa,
        statusKonsultasi,
        waktu,
        dataDetail
    )

}
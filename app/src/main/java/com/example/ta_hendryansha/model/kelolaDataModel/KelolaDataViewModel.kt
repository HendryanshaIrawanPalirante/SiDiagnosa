package com.example.ta_hendryansha.model.kelolaDataModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class KelolaDataViewModel(private val repository: UserRepository): ViewModel() {

    fun getDataGejala() = repository.getDataGejala()
    fun getDataPenyakit() = repository.getDataPenyakit()
    fun getDataRuleCf() = repository.getDataRuleCf()
    fun getDataRuleFc() = repository.getDataRuleFc()
    fun insertDataGejala(kodeGejala: String, namaGejala:String) = repository.insertDataGejala(kodeGejala, namaGejala)
    fun insertDataPenyakit(kodePenyakit: String, namaPenyakit: String) = repository.insertDataPenyakit(kodePenyakit, namaPenyakit)
    fun insertDataRuleCf(
        kodePenyakit: String,
        kodegejala: String,
        nilaiCf: String,
        intensitas: String,
        waktu: String
    ) = repository.insertDataRuleCf(kodePenyakit, kodegejala, nilaiCf, intensitas, waktu)
    fun insertDataRuleFc(
        idKeputusan: String,
        ya: String,
        tidak: String
    ) = repository.insertDataRuleFc(idKeputusan, ya, tidak)

    fun updateDataGejala(
        idGejala: String,
        kodeGejala: String,
        namaGejala: String
    ) = repository.updateDataGejala(idGejala, kodeGejala, namaGejala)

    fun updateDataPenyakit(
        idPenyakit: String,
        kodePenyakit: String,
        namaPenyakit: String
    ) = repository.updateDataPenyakit(idPenyakit, kodePenyakit, namaPenyakit)

    fun updateDataRuleCf(
        id: Int,
        kodePenyakit: String,
        kodeGejala: String,
        nilaiCf: String,
        intensitas: String,
        waktu: String
    ) = repository.updateDataRuleCf(id, kodePenyakit, kodeGejala, nilaiCf, intensitas, waktu)

    fun updateDataRuleFc(
        id: String,
        idGejala: String,
        ya: String,
        tidak: String
    ) = repository.updateDataRuleFc(id, idGejala, ya, tidak)

    fun getDataAdmin() = repository.getDataAdmin()

    fun getDataUser() = repository.getDataUser()

    fun insertAdmin(
        email: String,
        password: String,
        tipeAkun: String,
        nik: Int,
        namaLengkap: String,
        umur: Int,
        jenisKelamin: String,
        pekerjaan: String
    ) = repository.insertAdmin(email, password, tipeAkun, nik, namaLengkap, umur, jenisKelamin, pekerjaan)

    fun updateAdmin(
        idEmail: String,
        idNik: Int,
        email: String,
        password: String,
        tipeAkun: String,
        nik: Int,
        namaLengkap: String,
        umur: Int,
        jenisKelamin: String,
        pekerjaan: String
    ) = repository.updateAdmin(idEmail, idNik, email, password, tipeAkun, nik, namaLengkap, umur, jenisKelamin, pekerjaan)

    fun insertUser(
        email: String,
        password: String,
        tipeAkun: String,
        noBpjs: Int,
        namaLengkap: String,
        umur: Int,
        jenisKelamin: String,
        noKartuBerobat: String
    ) = repository.insertUser(email, password, tipeAkun, noBpjs, namaLengkap, umur, jenisKelamin, noKartuBerobat)

    fun updateUser(
        idEmail: String,
        idBpjs: Int,
        email: String,
        password: String,
        tipeAkun: String,
        noBpjs: Int,
        namaLengkap: String,
        umur: Int,
        jenisKelamin: String,
        noKartuBerobat: String
    ) = repository.UpdateUser(idEmail, idBpjs, email, password, tipeAkun, noBpjs, namaLengkap, umur, jenisKelamin, noKartuBerobat)

    fun kelolaNotifikasi(
        id: Int,
        nikDokter: Int,
        namaDokter: String,
        noBpjs: Int,
        noKartuBerobat: String?,
        namaPasien: String?,
        hasilDiagnosa: String?,
        umur: Int,
        jenisKelamin: String?,
        pengobatan: String,
        statusKonsultasi: String,
        waktu: String
    ) = repository.kelolaNotifikasi(id, nikDokter, namaDokter, noBpjs, noKartuBerobat, namaPasien, hasilDiagnosa, umur, jenisKelamin, pengobatan, statusKonsultasi, waktu)

    fun getNotifikasi() = repository.getNotifikasi()
}
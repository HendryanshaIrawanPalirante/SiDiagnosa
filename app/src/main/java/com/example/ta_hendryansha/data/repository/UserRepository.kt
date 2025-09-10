package com.example.ta_hendryansha.data.repository


import android.widget.RemoteViews.RemoteViewOutlineProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bumptech.glide.load.engine.Resource
import com.example.ta_hendryansha.remote.apiService.ApiService
import com.example.ta_hendryansha.remote.response.LoginResponse
import com.example.ta_hendryansha.remote.response.user.DaftarTungguKonsultasiResponse
import com.example.ta_hendryansha.remote.response.user.RuleFcResponse
import com.example.ta_hendryansha.remote.response.user.PenyakitResponse
import com.example.ta_hendryansha.remote.response.user.PertanyaanResponse
import com.example.ta_hendryansha.remote.response.RegisterResponse
import com.example.ta_hendryansha.remote.response.admin.GetAdminResponse
import com.example.ta_hendryansha.remote.response.admin.GetDataGejalaResponse
import com.example.ta_hendryansha.remote.response.admin.GetDataPenyakitResponse
import com.example.ta_hendryansha.remote.response.admin.GetDataRuleCfResponse
import com.example.ta_hendryansha.remote.response.admin.GetDataRuleFcResponse
import com.example.ta_hendryansha.remote.response.admin.InsertDataGejalaResponse
import com.example.ta_hendryansha.remote.response.admin.InsertDataPenyakitResponse
import com.example.ta_hendryansha.remote.response.admin.InsertDataRuleCfResponse
import com.example.ta_hendryansha.remote.response.admin.InsertDataRuleFcResponse
import com.example.ta_hendryansha.remote.response.admin.UpdateDataGejalaResponse
import com.example.ta_hendryansha.remote.response.admin.UpdateDataPenyakitResponse
import com.example.ta_hendryansha.remote.response.admin.UpdateDataRuleCfResponse
import com.example.ta_hendryansha.remote.response.admin.UpdateDataRuleFcResponse
import com.example.ta_hendryansha.remote.response.biodata.GetDataAdminResponse
import com.example.ta_hendryansha.remote.response.biodata.GetDataUserResponse
import com.example.ta_hendryansha.remote.response.biodata.InsertAdminResponse
import com.example.ta_hendryansha.remote.response.biodata.InsertUserResponse
import com.example.ta_hendryansha.remote.response.biodata.UpdateAdminResponse
import com.example.ta_hendryansha.remote.response.deleteData.DeleteDataResponse
import com.example.ta_hendryansha.remote.response.konsultasi.DetailGejalaResponse
import com.example.ta_hendryansha.remote.response.konsultasi.GetNotifikasiResponse
import com.example.ta_hendryansha.remote.response.konsultasi.HasilKonsultasiResponse
import com.example.ta_hendryansha.remote.response.konsultasi.KelolaNotifikasiResponse
import com.example.ta_hendryansha.remote.response.konsultasi.RiwayatPenyakitResponse
import com.example.ta_hendryansha.remote.response.user.GetUserResponse
import com.example.ta_hendryansha.remote.response.user.RiwayatKonsultasiResponse
import com.example.ta_hendryansha.remote.response.user.RuleCfResponse
import com.example.ta_hendryansha.remote.response.user.UpdateUserResponse
import com.example.ta_hendryansha.remote.response.user.UserKonsultasiResponse
import com.example.ta_hendryansha.utils.ResourceData
import retrofit2.http.Field

class UserRepository(
    private val apiService: ApiService
) {

    fun registerUser(
        noBpjs: Int,
        email: String,
        password:String,
        namaLengkap:String,
        umur: Int,
        jenisKelamin: String,
        noKartuBerobat: String,
        tipeAkun: String
    ): LiveData<ResourceData<RegisterResponse>> = liveData{
        try {
            val result = apiService.registerUser(noBpjs, email, password, namaLengkap, umur, jenisKelamin, noKartuBerobat, tipeAkun)
            emit(ResourceData.Success(result))
        }catch (e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun login(
        email: String,
        password: String
    ): LiveData<ResourceData<LoginResponse>> = liveData{
        try {
            val result = apiService.login(email, password)
            emit(ResourceData.Success(result))
        }catch (e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getUser(
        email: String
    ): LiveData<ResourceData<GetUserResponse>> = liveData {
        try{
            val result = apiService.getUser(email)
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun pertanyaan(
        kodeGejala: String
    ): LiveData<ResourceData<PertanyaanResponse>> = liveData {
        try {
            val result = apiService.pertanyaan(kodeGejala)
            emit(ResourceData.Success(result))
        }catch (e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun ruleFc(
        idKeputusan: String,
        action: String
    ): LiveData<ResourceData<RuleFcResponse>> = liveData {
        try {
            val result = apiService.ruleFc(idKeputusan, action)
            emit(ResourceData.Success(result))
        }catch (e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun ruleCf(
        kodePenyakit: String
    ): LiveData<ResourceData<RuleCfResponse>> = liveData{
        try{
            val result = apiService.ruleCf(kodePenyakit)
            emit(ResourceData.Success(result))
        }catch (e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun userKonsultasi(
        idUser: Int
    ): LiveData<ResourceData<UserKonsultasiResponse>> = liveData{
        try{
           val result = apiService.userKonsultasi(idUser)
           emit(ResourceData.Success(result))
        }catch (e: Exception){
            emit(ResourceData.Error(e))
        }
    }

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
    ): LiveData<ResourceData<RiwayatKonsultasiResponse>> = liveData {
        try{
            val result = apiService.konsultasiDoketer(noBpjs, namaPasien, umur, jenisKelamin, noKartuBerobat, hasilDiagnosa, statusKonsultasi, waktu, dataDetail)
            emit(ResourceData.Success(result))
        }catch (e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getPenyakit(
        kodePenyakit: String
    ): LiveData<ResourceData<PenyakitResponse>> = liveData {
        try{
            val result = apiService.getPenyakit(kodePenyakit)
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getDaftarKonsultasi(
        idUser: Int
    ): LiveData<ResourceData<DaftarTungguKonsultasiResponse>> = liveData {
        try {
            val result = apiService.daftarTungguKonsultasi(idUser)
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun updateUser(
        idEmail: String,
        idBpjs: Int,
        noBpjs: Int,
        email: String,
        password: String,
        namaLengkap: String,
        umur: Int,
        jenis_kelamin: String,
        noKartuBerobat: String
    ): LiveData<ResourceData<UpdateUserResponse>> = liveData {
        try{
            val result = apiService.updateUser(
                idEmail,
                idBpjs,
                noBpjs,
                email,
                password,
                namaLengkap,
                umur,
                jenis_kelamin,
                noKartuBerobat
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getDataGejala(): LiveData<ResourceData<GetDataGejalaResponse>> = liveData {
        try{
            val result = apiService.getDataGejala()
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getDataPenyakit(): LiveData<ResourceData<GetDataPenyakitResponse>> = liveData {
        try{
            val result = apiService.getDataPenyakit()
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getDataRuleCf(): LiveData<ResourceData<GetDataRuleCfResponse>> = liveData {
        try{
            val result = apiService.getDataRuleCf()
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getDataRuleFc(): LiveData<ResourceData<GetDataRuleFcResponse>> = liveData {
        try{
            val result = apiService.getDataRuleFc()
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun insertDataGejala(
        kodeGejala: String,
        namaGejala: String
    ): LiveData<ResourceData<InsertDataGejalaResponse>> = liveData {
        try{
            val result = apiService.insertDataGejala(
                kodeGejala, namaGejala
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun insertDataPenyakit(
        kodePenyakit: String,
        namaPenyakit: String
    ): LiveData<ResourceData<InsertDataPenyakitResponse>> = liveData {
        try{
            val result = apiService.insertDataPenyakit(
                kodePenyakit, namaPenyakit
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun insertDataRuleCf(
        kodePenyakit: String,
        kodegejala: String,
        nilaiCf: String,
        intensitas: String,
        waktu: String
    ): LiveData<ResourceData<InsertDataRuleCfResponse>> = liveData {
        try{
            val result = apiService.insertRuleCf(
                kodegejala,
                kodePenyakit,
                nilaiCf,
                intensitas,
                waktu
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun insertDataRuleFc(
        idKeputusan: String,
        ya: String,
        tidak: String
    ): LiveData<ResourceData<InsertDataRuleFcResponse>> = liveData {
        try{
            val result = apiService.insertRuleFc(
                idKeputusan,
                ya,
                tidak
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun updateDataGejala(
        idGejala: String,
        kodeGejala: String,
        namaGejala: String
    ): LiveData<ResourceData<UpdateDataGejalaResponse>> = liveData {
        try{
            val result = apiService.updateGejala(
                idGejala,
                namaGejala,
                kodeGejala
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun updateDataPenyakit(
        idPenyakit: String,
        kodePenyakit: String,
        namaPenyakit: String
    ): LiveData<ResourceData<UpdateDataPenyakitResponse>> = liveData {
        try{
            val result = apiService.updateDataPenyakit(
                idPenyakit, kodePenyakit, namaPenyakit
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun updateDataRuleCf(
        id: Int,
        kodePenyakit: String,
        kodeGejala: String,
        nilaiCf: String,
        intensitas: String,
        waktu: String
    ): LiveData<ResourceData<UpdateDataRuleCfResponse>> = liveData {
        try{
            val result = apiService.updateRuleCf(
                id, kodeGejala, kodePenyakit, nilaiCf, intensitas, waktu
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun updateDataRuleFc(
        id: String,
        idGejala: String,
        ya: String,
        tidak: String
    ): LiveData<ResourceData<UpdateDataRuleFcResponse>> = liveData {
        try{
            val result = apiService.updateRuleFc(
                id, idGejala, ya, tidak
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getDataAdmin(): LiveData<ResourceData<GetDataAdminResponse>> = liveData{
        try{
            val result = apiService.getDataAdmin()
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getDataUser(): LiveData<ResourceData<GetDataUserResponse>> = liveData {
        try{
            val result = apiService.getDataUser()
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun insertAdmin(
        email: String,
        password: String,
        tipeAkun: String,
        nik: Int,
        namaLengkap: String,
        umur: Int,
        jenisKelamin: String,
        pekerjaan: String
    ): LiveData<ResourceData<InsertAdminResponse>> = liveData {
        try{
            val reuslt = apiService.insertAdmin(
                email, password, tipeAkun, nik, namaLengkap, umur, jenisKelamin, pekerjaan
            )
            emit(ResourceData.Success(reuslt))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

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
    ): LiveData<ResourceData<UpdateAdminResponse>> = liveData {
        try{
            val result = apiService.updateAdmin(
                idEmail, idNik, email, password, tipeAkun, nik, namaLengkap, umur, jenisKelamin, pekerjaan
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun insertUser(
        email: String,
        password: String,
        tipeAkun: String,
        noBpjs: Int,
        namaLengkap: String,
        umur: Int,
        jenisKelamin: String,
        noKartuBerobat: String
    ): LiveData<ResourceData<InsertUserResponse>> = liveData {
        try{
            val result = apiService.insertUser(
                email, password, tipeAkun, noBpjs, namaLengkap, umur, jenisKelamin, noKartuBerobat
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun UpdateUser(
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
    ): LiveData<ResourceData<com.example.ta_hendryansha.remote.response.biodata.UpdateUserResponse>> = liveData {
        try{
            val result = apiService.UpdateUser(
                idEmail, idBpjs, email, password, tipeAkun, noBpjs, namaLengkap, umur, jenisKelamin, noKartuBerobat
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

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
    ): LiveData<ResourceData<KelolaNotifikasiResponse>> = liveData {
        try{
            val result = apiService.kelolaNotifikasi(
                id, nikDokter, namaDokter, noBpjs, noKartuBerobat, namaPasien, hasilDiagnosa, umur, jenisKelamin, pengobatan, statusKonsultasi, waktu
            )
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getNotifikasi(): LiveData<ResourceData<GetNotifikasiResponse>> = liveData {
        try{
            val result = apiService.getNotifikasi()
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getAdmin(email: String): LiveData<ResourceData<GetAdminResponse>> = liveData {
        try{
            val result = apiService.getAdmin(email)
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun deleteData(namaTabel: String, id: String, action: String): LiveData<ResourceData<DeleteDataResponse>> = liveData {
        try{
            val result = apiService.deleteData(namaTabel, id, action)
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getRiwayatHasilKonsultasi(): LiveData<ResourceData<HasilKonsultasiResponse>> = liveData {
        try{
            val result = apiService.getHasilKonsultasi()
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getRiwayatPenyakit(noBpjs: Int): LiveData<ResourceData<RiwayatPenyakitResponse>> = liveData {
        try{
            val result = apiService.getRiwayat(noBpjs)
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getDetailGejala(idDetail: String): LiveData<ResourceData<DetailGejalaResponse>> = liveData {
        try{
            val result = apiService.getDetailGejala(idDetail)
            emit(ResourceData.Success(result))
        }catch(e: Exception){
            emit(ResourceData.Error(e))
        }
    }

}
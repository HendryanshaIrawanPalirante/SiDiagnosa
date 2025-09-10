package com.example.ta_hendryansha.remote.apiService

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
import okio.FileNotFoundException
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("user/Register.php")
    suspend fun registerUser(
        @Field("no_bpjs") noBpjs: Int,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("nama_lengkap") namaLengkap: String,
        @Field("umur") umur: Int,
        @Field("jenis_kelamin") jenisKelamin: String,
        @Field("no_kartu_berobat") noKartuBerobat: String,
        @Field("tipe_akun") tipeAkun: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("Login.php")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("user/GetUser.php")
    suspend fun getUser(
        @Field("email") email: String
    ): GetUserResponse

    @GET("data/data_konsultasi/Pertanyaan.php")
    suspend fun pertanyaan(
        @Query("kode_gejala") kodeGejala: String
    ): PertanyaanResponse

    @GET("data/data_konsultasi/RuleFc.php")
    suspend fun ruleFc(
        @Query("kode_gejala") kodeGejala: String,
        @Query("action") action: String
    ): RuleFcResponse

    @GET("data/data_konsultasi/RuleCf.php")
    suspend fun ruleCf(
        @Query("kode_penyakit") kodePenyakit: String
    ): RuleCfResponse

    @FormUrlEncoded
    @POST("user/konsultasi/RiwayatKonsultasi.php")
    suspend fun konsultasiDoketer(
        @Field("no_bpjs") noBpjs: Int,
        @Field("nama_pasien") namaPasien: String,
        @Field("umur") umur: Int,
        @Field("jenis_kelamin") jenisKelamin: String,
        @Field("no_kartu_berobat") noKartuBerobat: String,
        @Field("hasil_diagnosa") hasilDiagnosa: String,
        @Field("status_konsultasi") statusKonsultasi: String,
        @Field("waktu_konsultasi") waktu: String,
        @Field("data_detail") dataDetail: String
    ): RiwayatKonsultasiResponse

    @FormUrlEncoded
    @POST("user/konsultasi/UserKonsultasi.php")
    suspend fun userKonsultasi(
        @Field("no_bpjs") idUser: Int
    ): UserKonsultasiResponse

    @GET("data/data_konsultasi/GetPenyakit.php")
    suspend fun getPenyakit(
        @Query("kode_penyakit") kodePenyakit: String
    ): PenyakitResponse

    @GET("user/konsultasi/DaftarRiwayatKonsultasi.php")
    suspend fun daftarTungguKonsultasi(
        @Query("id_user") idUser: Int
    ): DaftarTungguKonsultasiResponse

    @FormUrlEncoded
    @POST("user/biodata/UpdateBiodata.php")
    suspend fun updateUser(
        @Field("id_email") idEmail: String,
        @Field("id_bpjs") idBpjs: Int,
        @Field("no_bpjs") noBpjs: Int,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("nama_lengkap") namaLengkap: String,
        @Field("umur") umur: Int,
        @Field("jenis_kelamin") jenisKelamin: String,
        @Field("no_kartu_berobat") noKartuBerobat: String
    ): UpdateUserResponse

    @GET("data/data_penyakit/DataGejala.php")
    suspend fun getDataGejala(): GetDataGejalaResponse

    @GET("data/data_penyakit/DataPenyakit.php")
    suspend fun getDataPenyakit(): GetDataPenyakitResponse

    @GET("data/data_penyakit/DataRuleCf.php")
    suspend fun getDataRuleCf(): GetDataRuleCfResponse

    @GET("data/data_penyakit/DataRuleFc.php")
    suspend fun getDataRuleFc(): GetDataRuleFcResponse

    @FormUrlEncoded
    @POST("admin/insert/InsertGejala.php")
    suspend fun insertDataGejala(
        @Field("kode_gejala") kodeGejala: String,
        @Field("nama_gejala") namaGejala: String
    ): InsertDataGejalaResponse

    @FormUrlEncoded
    @POST("admin/update/UpdateGejala.php")
    suspend fun updateGejala(
        @Field("id_gejala") idGejala: String,
        @Field("nama_gejala") namaGejala: String,
        @Field("kode_gejala") kode_gejala: String,
    ): UpdateDataGejalaResponse

    @FormUrlEncoded
    @POST("admin/insert/InsertPenyakit.php")
    suspend fun insertDataPenyakit(
        @Field("kode_penyakit") kodePenyakit: String,
        @Field("nama_penyakit") namaPenyakit: String
    ): InsertDataPenyakitResponse

    @FormUrlEncoded
    @POST("admin/update/UpdatePenyakit.php")
    suspend fun updateDataPenyakit(
        @Field("id_penyakit") idPenyakit: String,
        @Field("kode_penyakit") kodePenyakit: String,
        @Field("nama_penyakit") namaPenyakit: String
    ): UpdateDataPenyakitResponse

    @FormUrlEncoded
    @POST("admin/insert/InsertRuleCf.php")
    suspend fun insertRuleCf(
        @Field("kode_gejala") kodeGejala: String,
        @Field("kode_penyakit") kodePenyakit: String,
        @Field("nilai_cf") nilaiCf: String,
        @Field("intensitas") intensitas: String,
        @Field("waktu") waktu: String
    ): InsertDataRuleCfResponse

    @FormUrlEncoded
    @POST("admin/update/UpdateRuleCf.php")
    suspend fun updateRuleCf(
        @Field("id") id: Int,
        @Field("kode_gejala") kodeGejala: String,
        @Field("kode_penyakit") kodePenyakit: String,
        @Field("nilai_cf") nilaiCf: String,
        @Field("intensitas") intensitas: String,
        @Field("waktu") waktu: String
    ): UpdateDataRuleCfResponse

    @FormUrlEncoded
    @POST("admin/insert/InsertRuleFc.php")
    suspend fun insertRuleFc(
        @Field("id_keputusan") idKeputusan: String,
        @Field("ya") ya: String,
        @Field("tidak") tidak: String
    ): InsertDataRuleFcResponse

    @FormUrlEncoded
    @POST("admin/update/UpdateRuleFc.php")
    suspend fun updateRuleFc(
        @Field("id") id: String,
        @Field("id_gejala") idGejala: String,
        @Field("ya") ya: String,
        @Field("tidak") tidak: String
    ): UpdateDataRuleFcResponse

    @GET("data/data_pengguna/GetDataAdmin.php")
    suspend fun getDataAdmin(): GetDataAdminResponse

    @GET("data/data_pengguna/GetDataUser.php")
    suspend fun getDataUser(): GetDataUserResponse

    @FormUrlEncoded
    @POST("admin/insert/InsertAdmin.php")
    suspend fun insertAdmin(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("tipe_akun") tipeAkun: String,
        @Field("nik") nik: Int,
        @Field("nama_lengkap") namaLengkap: String,
        @Field("umur") umur: Int,
        @Field("jenis_kelamin") jenisKelamin: String,
        @Field("pekerjaan") pekerjaan: String
    ): InsertAdminResponse

    @FormUrlEncoded
    @POST("admin/update/UpdateAdmin.php")
    suspend fun updateAdmin(
        @Field("id_email") idEmail: String,
        @Field("id_nik") idNik: Int,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("tipe_akun") tipeAkun: String,
        @Field("nik") nik: Int,
        @Field("nama_lengkap") namaLengkap: String,
        @Field("umur") umur: Int,
        @Field("jenis_kelamin") jenisKelamin: String,
        @Field("pekerjaan") pekerjaan: String
    ): UpdateAdminResponse

    @FormUrlEncoded
    @POST("admin/insert/InsertUser.php")
    suspend fun insertUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("tipe_akun") tipeAkun: String,
        @Field("no_bpjs") noBpjs: Int,
        @Field("nama_lengkap") namaLengkap: String,
        @Field("umur") umur: Int,
        @Field("jenis_kelamin") jenisKelamin: String,
        @Field("no_kartu_berobat") noKartuBerobat: String
    ): InsertUserResponse

    @FormUrlEncoded
    @POST("admin/update/UpdateUser.php")
    suspend fun UpdateUser(
        @Field("id_email") idEmail: String,
        @Field("id_bpjs") idBpjs: Int,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("tipe_akun") tipeAkun: String,
        @Field("no_bpjs") noBpjs: Int,
        @Field("nama_lengkap") namaLengkap: String,
        @Field("umur") umur: Int,
        @Field("jenis_kelamin") jenisKelamin: String,
        @Field("no_kartu_berobat") noKartuBerobat: String
    ): com.example.ta_hendryansha.remote.response.biodata.UpdateUserResponse

    @GET("admin/konsultasi/HasilKonsultasi.php")
    suspend fun getHasilKonsultasi(): HasilKonsultasiResponse

    @FormUrlEncoded
    @POST("admin/konsultasi/KelolaNotifikasi.php")
    suspend fun kelolaNotifikasi(
        @Field("id") id: Int,
        @Field("nik_dokter") nikDokter: Int,
        @Field("nama_dokter") namaDokter: String,
        @Field("no_bpjs") noBpjs: Int,
        @Field("no_kartu_berobat") noKartuBerobat: String?,
        @Field("nama_pasien") namaPasien: String?,
        @Field("hasil_diagnosa") hasilDiagnosa: String?,
        @Field("umur") umur: Int,
        @Field("jenis_kelamin") jenisKelamin: String?,
        @Field("pengobatan") pengobatan: String,
        @Field("status_konsultasi") statusKonsultasi: String,
        @Field("waktu") waktu: String
    ): KelolaNotifikasiResponse

    @GET("admin/konsultasi/Notifikasi.php")
    suspend fun getNotifikasi(): GetNotifikasiResponse

    @FormUrlEncoded
    @POST("admin/GetAdmin.php")
    suspend fun getAdmin(
        @Field("email") email: String
    ): GetAdminResponse

    @FormUrlEncoded
    @POST("admin/delete/DeleteData.php")
    suspend fun deleteData(
        @Field("nama_tabel") namaTabel: String,
        @Field("id") id: String,
        @Field("action") action: String
    ): DeleteDataResponse

    @GET("user/konsultasi/RiwayatPenyakit.php")
    suspend fun getRiwayat(
        @Query("no_bpjs") noBpjs: Int
    ): RiwayatPenyakitResponse

    @FormUrlEncoded
    @POST("admin/konsultasi/GetDetailGejala.php")
    suspend fun getDetailGejala(
        @Field("id_detail") idDetail: String
    ): DetailGejalaResponse

}
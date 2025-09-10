package com.example.ta_hendryansha.view.user.hasilDiagnosa

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.adapter.konsultasi.DetailGejalaAdapter
import com.example.ta_hendryansha.databinding.ActivityHasilDiagnosaBinding
import com.example.ta_hendryansha.entity.GejalaKonsultasi
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.UserViewModel
import com.example.ta_hendryansha.model.factoryModel.ViewModelFactory
import com.example.ta_hendryansha.model.konsultasiModel.HasilKonsultasiViewModel
import com.example.ta_hendryansha.model.konsultasiModel.KonsultasiDokterViewModel
import com.example.ta_hendryansha.model.konsultasiModel.KonsultasiViewModel
import com.example.ta_hendryansha.model.userModel.UserKonsultasiViewModel
import com.example.ta_hendryansha.preference.UserPreference
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.EXTRA_DESKRIPSI
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.EXTRA_INTENSITAS
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.EXTRA_KODE
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.EXTRA_WAKTU
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.GEJALA_DIPILIH
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.properties.Delegates
import kotlinx.coroutines.*

class HasilDiagnosaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityHasilDiagnosaBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val hasilKonsultasiViewModel: HasilKonsultasiViewModel by viewModels {
        UserModelFactory()
    }
    private val userKonsultasiViewModel: UserKonsultasiViewModel by viewModels {
        UserModelFactory()
    }
    private val konsultasiDokterViewModel: KonsultasiDokterViewModel by viewModels {
        UserModelFactory()
    }
    private val userViewModel: UserViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private val konsultasiViewModel: KonsultasiViewModel by viewModels {
        UserModelFactory()
    }
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "tugas channel"
    }
    private var kodePenyakit: String? = null
    private var ruleKodeGejala: String? = null
    private var idBpjs: Int = 0
    private var arrayListGejala = ArrayList<String>()
    private var cfKombinasi by Delegates.notNull<Double>()
    private var cf by Delegates.notNull<Double>()
    private var penyakit: String? = null
    private var deskripsi = hashMapOf<String?, String?>()
    private var waktu = hashMapOf<String?, String?>()
    private var intensitas = hashMapOf<String?, String?>()

    @SuppressLint("SetTextI18n")
    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilDiagnosaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sendNotification()

        kodePenyakit = intent.getStringExtra(EXTRA_KODE)
        intent.getStringArrayListExtra(GEJALA_DIPILIH)?.let { arrayListGejala.addAll(it) }
        intent.getSerializableExtra(EXTRA_DESKRIPSI)?.let {
            val receivedDeskripsi = it as HashMap<String?, String?>
            deskripsi.putAll(receivedDeskripsi)
        }
        intent.getSerializableExtra(EXTRA_WAKTU)?.let {
            val receivedWaktu = it as HashMap<String?, String?>
            waktu.putAll(receivedWaktu)
        }
        intent.getSerializableExtra(EXTRA_INTENSITAS)?.let {
            val receivedIntensitas = it as HashMap<String?, String?>
            intensitas.putAll(receivedIntensitas)
        }

        if(kodePenyakit == "P000"){
            binding.tvHasil.text = "Anda tidak menderita penyakit apapun"
            binding.btnKonsultasi.visibility = View.GONE
        }else{
            userViewModel.getUser().observe(this){user ->
                hasilKonsultasiViewModel.getUser(user.email).observe(this){ userData ->
                    if(userData != null){
                        when(userData){
                            is ResourceData.Loading ->{

                            }
                            is ResourceData.Success ->{
                                val dataUser = userData.data
                                if(dataUser?.error == true){

                                }else{
                                    if(dataUser != null){
                                        idBpjs = dataUser.data.noBpjs
                                    }
                                }
                                kodePenyakit?.let { getRuleCf(it) }
                            }
                            is ResourceData.Error ->{

                            }
                        }
                    }

                }
            }
        }

    }

    fun getRuleCf(kodeGejala: String){
        hasilKonsultasiViewModel.ruleCf(kodeGejala).observe(this){
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{
                    }
                    is ResourceData.Success ->{
                        val dataRule = it.data
                        if(dataRule?.error == true){

                        }else{
                            if (dataRule != null) {
                                cfKombinasi = 0.0
                                var i = 0
                                for(gejala in arrayListGejala){
                                    for(rule in dataRule.data){
                                        ruleKodeGejala = rule.kodeGejala
                                        cf = rule.nilaiCf
                                        if (gejala == ruleKodeGejala && intensitas[gejala] == rule.intensitas && waktu[gejala] == rule.waktu) {
                                            if (i > 1) {
                                                cfKombinasi += cf * (1 - cfKombinasi)
                                            } else if (i == 1) {
                                                cfKombinasi += cf * (1 - cfKombinasi)
                                            } else {
                                                cfKombinasi += cf
                                            }
                                            i++
                                        }
                                    }
                                }
                                setView()
                            }
                        }
                    }
                    is ResourceData.Error ->{
                    }
                }
            }
        }
    }

    private fun setView(){
        val nilaiCf = cfKombinasi * 100
        val format = DecimalFormat("#.0")
        val hasil = format.format(nilaiCf)
        val presentase = "${hasil}%"

        kodePenyakit?.let {
            hasilKonsultasiViewModel.getPenyakit(it).observe(this){ dataPenyakit ->
                if(dataPenyakit != null){
                    when(dataPenyakit){
                        is ResourceData.Loading ->{
                        }
                        is ResourceData.Success ->{
                            val penyakitResult = dataPenyakit.data
                            if(penyakitResult?.error == true){

                            }else{
                                penyakit = penyakitResult?.data
                                binding.tvHasil.text = penyakitResult?.data
                                binding.tvPresentase.text = presentase
                                setDetailGejala()
                                binding.btnKonsultasi.setOnClickListener(this)
                            }
                        }
                        is ResourceData.Error ->{

                        }
                    }
                }
            }
        }
    }

    private fun getUser(){
        userKonsultasiViewModel.getUser(idBpjs).observe(this){
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{
                    }
                    is ResourceData.Success ->{
                        val dataUser = it.data
                        if(dataUser != null){
                            val noBpjs = dataUser.data.noBpjs
                            val namaPasien = dataUser.data.namaLengkap
                            val umur = dataUser.data.umur
                            val jenisKelamin = dataUser.data.jenisKelamin
                            val noKartuBerobat = dataUser.data.noKartuBerobat
                            val statusKonsultasi = "Menunggu"
                            val waktu = getCurrentDate()
                            val listDataDetail = getDataDetail()
                            val gson = Gson()
                            val jsonString = gson.toJson(listDataDetail)
                            penyakit?.let { it1 ->
                                konsultasiDokterViewModel.konsultasiDokter(noBpjs, namaPasien, umur, jenisKelamin, noKartuBerobat,
                                    it1, statusKonsultasi, waktu, jsonString).observe(this){riwayat ->
                                    if(riwayat != null){
                                        when(riwayat){
                                            is ResourceData.Loading ->{

                                            }
                                            is ResourceData.Success ->{
                                                Log.d("HasilDiagnosaActivity", "Berhasil")
                                                val dataRiwayat = riwayat.data
                                                if(dataRiwayat?.error == true){
                                                    Toast.makeText(
                                                        this,
                                                        dataRiwayat.pesan,
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }else{
                                                    Toast.makeText(
                                                        this,
                                                        dataRiwayat?.pesan,
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                            is ResourceData.Error ->{
//                                                try{
//                                                    Toast.makeText(
//                                                        this,
//                                                        riwayat.message.message,
//                                                        Toast.LENGTH_SHORT
//                                                    ).show()
//                                                }catch (e: Exception){
//                                                    Toast.makeText(
//                                                        this,
//                                                        e.toString(),
//                                                        Toast.LENGTH_SHORT
//                                                    ).show()
//                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                    is ResourceData.Error ->{

                    }
                }
            }
        }
    }

    private fun setDetailGejala(){
        val listDetail = ArrayList<GejalaKonsultasi>()
        lifecycleScope.launch {
            for(gejala in arrayListGejala){
                val namaGejala = getGejala(gejala)
                val data = GejalaKonsultasi(
                    namaGejala,
                    deskripsi[gejala],
                    waktu[gejala],
                    intensitas[gejala]
                )
                listDetail.add(data)
            }

            binding.rvGejala.layoutManager = LinearLayoutManager(this@HasilDiagnosaActivity)
            binding.rvGejala.setHasFixedSize(true)
            val listGejalaAdapter = DetailGejalaAdapter(listDetail)
            binding.rvGejala.adapter = listGejalaAdapter
        }
    }

    private fun getDataDetail(): ArrayList<GejalaKonsultasi>{
        val listDetail = ArrayList<GejalaKonsultasi>()
            for(gejala in arrayListGejala){
                val data = GejalaKonsultasi(
                    gejala,
                    deskripsi[gejala],
                    waktu[gejala],
                    intensitas[gejala]
                )
                listDetail.add(data)
            }
        return listDetail
    }


    private suspend fun getGejala(kodeGejala: String): String{
        return suspendCoroutine {continuation ->
            konsultasiViewModel.pertanyaan(kodeGejala).observe(this){ it ->
                if(it != null){
                    when(it){
                        is ResourceData.Loading ->{
                        }
                        is ResourceData.Success ->{
                            val dataGejala = it.data
                            if(dataGejala?.error == true){
                                Toast.makeText(
                                    this,
                                    dataGejala.pesan,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else{
                                val result = dataGejala?.data?.namaGejala
                                result?.let {
                                    continuation.resume(it)
                                }
                            }
                        }
                        is ResourceData.Error ->{
                            val message = "Gagal Mendapatkan Gejala"
                            Toast.makeText(
                                this,
                                message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }

    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnKonsultasi ->{
                getUser()
//                val intent = Intent(this@HasilDiagnosaActivity, DaftarTungguKonsultasiActivity::class.java)
//                startActivity(intent)
                finish()
            }
        }
    }

    private fun sendNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Konsultasi")
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setContentText("Tunggu pemeriksaan dokter dan segera memeriksakan diri di puskesmas")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSubText("Reminder")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

}
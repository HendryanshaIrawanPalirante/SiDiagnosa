package com.example.ta_hendryansha.view.user.riwayatKonsultasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityDetailRiwayatBinding
import com.example.ta_hendryansha.entity.HasilKonulstasi
import com.example.ta_hendryansha.entity.RiwayatPenyakit
import com.example.ta_hendryansha.view.admin.hasilKonsultasi.AddUpdateHasilKonsultasiActivity
import com.example.ta_hendryansha.view.admin.kelola_user.AddUpdateUserActivity

class DetailRiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRiwayatBinding
    private var riwayatPenyakit: RiwayatPenyakit? = null
    private var position: Int = 0
    companion object {
        const val EXTRA_RIWAYAT = "extra_riwayat"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        riwayatPenyakit = intent.getParcelableExtra(EXTRA_RIWAYAT)

        if(riwayatPenyakit != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)
        }else{
            riwayatPenyakit = RiwayatPenyakit()
        }

        riwayatPenyakit?.let {
            binding.tvNama.text = it.namaPasien
            binding.tvNoBpjs.text = it.noBpjs.toString()
            binding.tvHasilDiagnosa.text = it.hasilDiagnosa
            binding.tvWaktu.text = it.waktu
            binding.tvPengobatan.text = it.pengobatan
        }

    }
}
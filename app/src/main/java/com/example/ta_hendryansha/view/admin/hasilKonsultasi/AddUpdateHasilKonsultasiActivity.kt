package com.example.ta_hendryansha.view.admin.hasilKonsultasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityAddUpdateHasilKonsultasiBinding
import com.example.ta_hendryansha.entity.HasilKonulstasi
import com.example.ta_hendryansha.entity.User
import com.example.ta_hendryansha.view.admin.kelola_user.AddUpdateUserActivity

class AddUpdateHasilKonsultasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateHasilKonsultasiBinding

    private var hasilKonsultasi: HasilKonulstasi? = null
    private var position: Int = 0
    companion object {
        const val EXTRA_HASIl_KONSULTASI = "extra_hasil_konsultasi"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateHasilKonsultasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hasilKonsultasi = intent.getParcelableExtra(EXTRA_HASIl_KONSULTASI)

        if(hasilKonsultasi != null){
            position = intent.getIntExtra(AddUpdateUserActivity.EXTRA_POSITION, 0)
        }else{
            hasilKonsultasi = HasilKonulstasi()
        }

        hasilKonsultasi?.let {
            binding.tvNama.text = it.namaPasien
            binding.tvNoBpjs.text = it.noBpjs.toString()
            binding.tvNoKartuBerobat.text = it.noKartuBerobat
            binding.tvUmur.text = it.umur.toString()
            binding.tvJenisKelamin.text = it.jenisKelamin
            binding.tvHasilDiagnosa.text = it.hasilDiagnosa
            binding.tvWaktu.text = it.waktu
            binding.tvNikDokter.text = it.nikDokter.toString()
            binding.tvNamaDokter.text = it.namaDokter
            binding.tvPengobatan.text = it.pengobatan
        }

    }
}
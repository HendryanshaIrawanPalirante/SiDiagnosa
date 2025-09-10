package com.example.ta_hendryansha.view.user.konsultasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityKonsultasiBinding
import com.example.ta_hendryansha.view.main.MainActivity

class KonsultasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKonsultasiBinding
    private var kodeGejala: String? = null

    companion object{
        const val EXTRA_KODE = "extra_kode"
        const val EXTRA_DESKRIPSI = "extra_deskripsi"
        const val EXTRA_WAKTU = "extra+waktu"
        const val EXTRA_INTENSITAS = "extra_intensitas"
        const val HITUNG = "hitung"
        const val GEJALA_DIPILIH = "gejala_dipilih"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKonsultasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        kodeGejala = intent.getStringExtra(EXTRA_KODE)

        val bundle = Bundle()
        bundle.putString(EXTRA_KODE, kodeGejala)

        val fragmentManager = supportFragmentManager
        val konsultasiFragment = KonsultasiFragment()
            konsultasiFragment.arguments = bundle
        val fragment = fragmentManager.findFragmentByTag(KonsultasiFragment::class.java.simpleName)

        if (fragment !is KonsultasiFragment) {
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, konsultasiFragment, KonsultasiFragment::class.java.simpleName)
                .addToBackStack(MainActivity::class.java.simpleName)
                .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAndRemoveTask()
    }
}
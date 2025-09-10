package com.example.ta_hendryansha.view.user.konsultasi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.FragmentKonsultasiBinding
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.konsultasiModel.KonsultasiViewModel
import com.example.ta_hendryansha.remote.response.RegisterResponse
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.main.MainActivity
import com.example.ta_hendryansha.view.user.hasilDiagnosa.HasilDiagnosaActivity
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.EXTRA_DESKRIPSI
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.EXTRA_INTENSITAS
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.EXTRA_KODE
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.EXTRA_WAKTU
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.GEJALA_DIPILIH
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.HITUNG
import com.google.gson.Gson
import retrofit2.HttpException

class KonsultasiFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentKonsultasiBinding? = null
    private val binding get() = _binding!!
    private val konsultasiViewModel: KonsultasiViewModel by viewModels {
        UserModelFactory()
    }
    private var count: Int? = 1
    private var arrayListGejala = ArrayList<String>()
    private var deskripsiGejala = hashMapOf<String?, String?>()
    private var waktuGejala = hashMapOf<String?, String?>()
    private var intensitasGejala = hashMapOf<String?, String?>()
    private var namaGejala: String? = null
    private var kodeGejala: String? = null
    private var nextSoal: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentKonsultasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null){
            Log.d("KonsultasiFragment", "Argument Tidal Null")
            kodeGejala = arguments?.getString(EXTRA_KODE)
            count = arguments?.getInt(HITUNG, 1)
            requireArguments().getStringArrayList(GEJALA_DIPILIH)?.let { arrayListGejala.addAll(it) }
            arguments?.getSerializable(EXTRA_DESKRIPSI)?.let {
                val receivedDeskripsi = it as HashMap<String, String>
                deskripsiGejala.putAll(receivedDeskripsi)
            }
            arguments?.getSerializable(EXTRA_WAKTU)?.let {
                val receivedWaktu = it as HashMap<String, String>
                waktuGejala.putAll(receivedWaktu)
            }
            arguments?.getSerializable(EXTRA_INTENSITAS)?.let {
                val receivedIntensitas = it as HashMap<String, String>
                intensitasGejala.putAll(receivedIntensitas)
            }
        }

        Log.d("KonsultasiFragment", "Data Berhasil $kodeGejala")
        Log.d("KonsultasiFragment", "Data Berhasil $count")

        if(kodeGejala?.substring(0,1) ==  "P"){
            val intent = Intent(requireActivity(), HasilDiagnosaActivity::class.java)
            intent.putExtra(EXTRA_KODE, kodeGejala)
            intent.putExtra(EXTRA_DESKRIPSI, deskripsiGejala)
            intent.putExtra(EXTRA_WAKTU, waktuGejala)
            intent.putExtra(EXTRA_INTENSITAS, intensitasGejala)
            intent.putStringArrayListExtra(GEJALA_DIPILIH, arrayListGejala)
            startActivity(intent)
            requireActivity().finish()
        }else{
            kodeGejala?.let {
                getGejala(it)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getGejala(kodeGejala: String){
        konsultasiViewModel.pertanyaan(kodeGejala).observe(viewLifecycleOwner){ it ->
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{
                    }
                    is ResourceData.Success ->{
                        val dataGejala = it.data
                        if(dataGejala?.error == true){
                            Toast.makeText(
                                requireContext(),
                                dataGejala.pesan,
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            val result = dataGejala?.data?.namaGejala
                            result?.let {
                                namaGejala = it
                                binding.soal.text = "Anda mengalami gejala $namaGejala?"
                                binding.judulSoal.text = "Pertanyaan ke - $count"
                            }
                            binding.btnYa.setOnClickListener(this)
                            binding.btnTidak.setOnClickListener(this)
                        }
                    }
                    is ResourceData.Error ->{
                        var message = "Gagal Mendapatkan Gejala"
                        try{
                            Gson().fromJson(
                                (it.message as HttpException).response()?.errorBody()?.string(), RegisterResponse::class.java
                            ).pesan.let {error ->
                                message = error
                            }
                        }catch (e: Exception){
                            Toast.makeText(
                                requireContext(),
                                e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        Toast.makeText(
                            requireContext(),
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_ya -> {
                pindahFragment("ya")
            }
            R.id.btn_tidak -> {
                kodeGejala?.let { getNextGejala(it, "tidak") }
            }
        }
    }

    private fun pindahFragment(jawaban: String){
        if(jawaban == "ya"){
            kodeGejala?.let { arrayListGejala.add(it) }

            val openDetailKonsultasi = DetailKonsultasiFragment()
            val fragmentManager = childFragmentManager
            openDetailKonsultasi.show(fragmentManager, DetailKonsultasiFragment::class.java.simpleName)
        }

    }

    fun getNextGejala(kodeGejala: String, action: String){
        konsultasiViewModel.ruleFc(kodeGejala, action).observe(viewLifecycleOwner){ it ->
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{
                    }
                    is ResourceData.Success ->{
                        val dataSoal = it.data
                        if(dataSoal?.error == true){
                            Toast.makeText(
                                requireContext(),
                                dataSoal.pesan,
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            val result = dataSoal?.keputusan
                            result.let {
                                nextSoal = it
                                move()
                            }
                            binding.btnYa.setOnClickListener(this)
                            binding.btnTidak.setOnClickListener(this)
                        }
                    }
                    is ResourceData.Error ->{
                        var message = "Gagal Mendapatkan Gejala"
                        try{
                            Gson().fromJson(
                                (it.message as HttpException).response()?.errorBody()?.string(), RegisterResponse::class.java
                            ).pesan.let {error ->
                                message = error
                            }
                        }catch (e: Exception){
                            Toast.makeText(
                                requireContext(),
                                e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        Toast.makeText(
                            requireContext(),
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun move(){
        Log.d("KonsultasiFragment", "Next Soal sekian $nextSoal")
        val bundle = Bundle()
        bundle.putString(EXTRA_KODE, nextSoal)
        bundle.putSerializable(EXTRA_DESKRIPSI, deskripsiGejala)
        bundle.putSerializable(EXTRA_WAKTU, waktuGejala)
        bundle.putSerializable(EXTRA_INTENSITAS, intensitasGejala)
        bundle.putInt(HITUNG, count?.plus(1) ?: 0)
        bundle.putStringArrayList(GEJALA_DIPILIH, arrayListGejala)

        val konsultasiFragment = KonsultasiFragment()
        konsultasiFragment.arguments = bundle

        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, konsultasiFragment, KonsultasiFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }

    internal var detailKonsultasi = object : DetailKonsultasiFragment.DetailKonsultasi {
        override fun dataKonsultasi(deskripsi: String?, waktu: String?, intensitas: String?) {
            deskripsiGejala[kodeGejala] = deskripsi
            waktuGejala[kodeGejala] = waktu
            intensitasGejala[kodeGejala] = intensitas
            kodeGejala?.let { getNextGejala(it, "ya") }
        }
    }

}
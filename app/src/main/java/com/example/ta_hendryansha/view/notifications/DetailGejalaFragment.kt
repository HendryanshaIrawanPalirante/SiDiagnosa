package com.example.ta_hendryansha.view.notifications

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.adapter.konsultasi.DetailGejalaAdapter
import com.example.ta_hendryansha.databinding.FragmentDetailGejalaBinding
import com.example.ta_hendryansha.databinding.FragmentKonsultasiBinding
import com.example.ta_hendryansha.entity.GejalaKonsultasi
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.konsultasiModel.KonsultasiViewModel
import com.example.ta_hendryansha.model.notifikasiModel.NotifikasiViewModel
import com.example.ta_hendryansha.utils.ResourceData
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DetailGejalaFragment : DialogFragment() {

    private var _binding: FragmentDetailGejalaBinding? = null
    private val binding get() = _binding!!
    private var receivedData: String = ""
    private val konsultasiViewModel: KonsultasiViewModel by viewModels {
        UserModelFactory()
    }
    private val notifikasiViewModel: NotifikasiViewModel by viewModels {
        UserModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDetailGejala(receivedData)

    }
    private val listDetail = ArrayList<GejalaKonsultasi>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailGejalaBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setData(data: String) {
        this.receivedData = data
    }

    private fun setDetailGejala(listData: ArrayList<GejalaKonsultasi>){
        val listDetail = ArrayList<GejalaKonsultasi>()
        lifecycleScope.launch {
            for(data in listData){
                val namaGejala = getGejala(data.namaGejala)
                val dataResult = GejalaKonsultasi(
                    namaGejala,
                    data.deskripsi,
                    data.waktu,
                    data.intensitas
                )
                listDetail.add(dataResult)
            }

            binding.rvGejala.layoutManager = LinearLayoutManager(requireContext())
            binding.rvGejala.setHasFixedSize(true)
            val listGejalaAdapter = DetailGejalaAdapter(listDetail)
            binding.rvGejala.adapter = listGejalaAdapter
        }
    }

    private fun getDetailGejala(id: String){
        notifikasiViewModel.getDetail(id).observe(this){ detailData ->
            if(detailData != null){
                when(detailData){
                    is ResourceData.Loading ->{
                    }
                    is ResourceData.Success ->{
                        val dataDetail = detailData.data
                        if(dataDetail?.error == true){
                            Toast.makeText(
                                requireContext(),
                                dataDetail.pesan,
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            if(dataDetail?.data != null){
                                for(result in dataDetail.data){
                                    val detailKonsultasi = GejalaKonsultasi(
                                        result.namaGejala,
                                        result.deskripsi,
                                        result.waktu,
                                        result.intensitas
                                    )
                                    listDetail.add(detailKonsultasi)
                                }
                            }
                            setDetailGejala(listDetail)
                        }
                    }
                    is ResourceData.Error ->{

                    }
                }
            }
        }
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
                                    requireContext(),
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
                                requireContext(),
                                message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }

    }

}
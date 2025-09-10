package com.example.ta_hendryansha.view.user.konsultasi

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.adapter.konsultasi.DaftarKonsultasiAdapter
import com.example.ta_hendryansha.databinding.ActivityDaftarTungguKonsultasiBinding
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.UserViewModel
import com.example.ta_hendryansha.model.factoryModel.ViewModelFactory
import com.example.ta_hendryansha.model.daftarTungguKonsultasiModel.DaftarTungguKonsultasiViewModel
import com.example.ta_hendryansha.preference.UserPreference
import com.example.ta_hendryansha.remote.response.user.DataItemKonsultasi
import com.example.ta_hendryansha.utils.ResourceData

class DaftarTungguKonsultasiActivity : AppCompatActivity() {

    private lateinit var konsultasiAdapter: DaftarKonsultasiAdapter
    private lateinit var binding: ActivityDaftarTungguKonsultasiBinding
    private var listKonsultasi = ArrayList<DataItemKonsultasi>()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val mainViewModel: UserViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private val daftarTungguKonsultasiViewModel: DaftarTungguKonsultasiViewModel by viewModels {
        UserModelFactory()
    }
    private var idBpjs: Int = 0
    private lateinit var rvPenyakit: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarTungguKonsultasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setInitLayout()

        mainViewModel.getUser().observe(this){
            daftarTungguKonsultasiViewModel.getUser(it.email).observe(this){userData ->
                if(userData != null){
                    when(userData){
                        is ResourceData.Loading ->{

                        }
                        is ResourceData.Success ->{
                            val data = userData.data
                            if(data != null){
                                idBpjs = data.data.noBpjs
                            }
                            dataListKonsultasi()
                        }
                        is ResourceData.Error ->{

                        }
                    }
                }
            }
        }
    }

    private fun dataListKonsultasi(){
        daftarTungguKonsultasiViewModel.getListKonsultasi(idBpjs).observe(this){
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{

                    }
                    is ResourceData.Success ->{
                        it.data?.let { it1 -> listKonsultasi.addAll(it1.data) }
                        setMenu()
                    }
                    is ResourceData.Error ->{

                    }
                }
            }
        }
    }

    private fun setInitLayout(){
        rvPenyakit = findViewById(R.id.rv_penyakit)
        rvPenyakit.setHasFixedSize(true)
        rvPenyakit.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setMenu(){
        konsultasiAdapter = DaftarKonsultasiAdapter(listKonsultasi)
        rvPenyakit.adapter = konsultasiAdapter
        konsultasiAdapter.notifyDataSetChanged()
    }

}
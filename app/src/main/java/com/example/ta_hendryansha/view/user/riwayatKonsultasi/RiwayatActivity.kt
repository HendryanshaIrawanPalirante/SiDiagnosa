package com.example.ta_hendryansha.view.user.riwayatKonsultasi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.adapter.kelola_data.GejalaAdapter
import com.example.ta_hendryansha.adapter.kelola_data.RiwayatPenyakitAdapter
import com.example.ta_hendryansha.databinding.ActivityRiwayatBinding
import com.example.ta_hendryansha.entity.Gejala
import com.example.ta_hendryansha.entity.RiwayatPenyakit
import com.example.ta_hendryansha.entity.User
import com.example.ta_hendryansha.model.UserViewModel
import com.example.ta_hendryansha.model.biodataModel.BiodataUserViewModel
import com.example.ta_hendryansha.model.deleteDataModel.DeleteDataViewModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.factoryModel.ViewModelFactory
import com.example.ta_hendryansha.model.riwayatPenyakitModel.RiwayatPenyakitViewModel
import com.example.ta_hendryansha.preference.UserPreference
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.admin.kelola_data.ListGejalaActivity
import com.example.ta_hendryansha.view.user.riwayatKonsultasi.DetailRiwayatActivity.Companion.EXTRA_POSITION
import com.example.ta_hendryansha.view.user.riwayatKonsultasi.DetailRiwayatActivity.Companion.EXTRA_RIWAYAT
import com.google.android.material.snackbar.Snackbar

class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val userViewModel: UserViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(datastore))
    }
    private val biodataUserViewModel: BiodataUserViewModel by viewModels {
        UserModelFactory()
    }
    private val riwayatPenyakitViewModel: RiwayatPenyakitViewModel by viewModels {
        UserModelFactory()
    }
    private val deleteDataViewModel: DeleteDataViewModel by viewModels {
        UserModelFactory()
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    private lateinit var adapter: RiwayatPenyakitAdapter
    private val listRiwayat = ArrayList<RiwayatPenyakit>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRiwayat.layoutManager = LinearLayoutManager(this)
        binding.rvRiwayat.setHasFixedSize(true)

        adapter = RiwayatPenyakitAdapter(object : RiwayatPenyakitAdapter.OnItemClickCallback{
            override fun onItemClicked(selectedRiwayat: RiwayatPenyakit?, position: Int) {
                val intent = Intent(this@RiwayatActivity, DetailRiwayatActivity::class.java)
                intent.putExtra(EXTRA_RIWAYAT, selectedRiwayat)
                intent.putExtra(EXTRA_POSITION, position)
                startActivity(intent)
            }
        })

        adapter.setOnDeleteClickCallback(object : RiwayatPenyakitAdapter.OnDeleteClickCallback{
            override fun onDeleteClicked(selectedRiwayat: RiwayatPenyakit?, position: Int) {
                deleteData(selectedRiwayat, position)
                adapter.removeItem(position)
                Snackbar.make(binding.rvRiwayat, "Satu item berhasil dihapus", Snackbar.LENGTH_SHORT).show()
            }
        })


        binding.rvRiwayat.adapter = adapter

        if (savedInstanceState == null) {
            loadRiwayat()
        } else {
            val list = savedInstanceState.getParcelableArrayList<RiwayatPenyakit>(EXTRA_STATE)
            if (list != null) {
                adapter.listRiwayat = list
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun loadRiwayat(){
        userViewModel.getUser().observe(this){
            biodataUserViewModel.getUser(it.email).observe(this){dataUser ->
                if(dataUser != null){
                    when(dataUser){
                        is ResourceData.Loading ->{

                        }
                        is ResourceData.Success ->{
                            val nobpjs = dataUser.data?.data?.noBpjs
                            if (nobpjs != null) {
                                setData(nobpjs)
                            }
                        }
                        is ResourceData.Error ->{

                        }
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setData(noBpjs: Int){
        riwayatPenyakitViewModel.getRiwayatPenyakit(noBpjs).observe(this){ dataRiwayat ->
            if(dataRiwayat != null){
                when(dataRiwayat){
                    is ResourceData.Loading ->{

                    }
                    is ResourceData.Success -> {
                        val data = dataRiwayat.data
                        if (data?.error == true) {

                        } else {
                            val newListRiwayat = ArrayList<RiwayatPenyakit>()
                            if (data != null) {
                                for (dataResult in data.data) {
                                    val dataRiwayatPenyakit = RiwayatPenyakit(
                                        dataResult.id,
                                        dataResult.namaLengkap,
                                        dataResult.noBpjs,
                                        dataResult.jenisKelamin,
                                        dataResult.umur,
                                        dataResult.hasilDiagnosa,
                                        dataResult.pengobatan,
                                        dataResult.statusKonsultasi,
                                        dataResult.waktu
                                    )
                                    newListRiwayat.add(dataRiwayatPenyakit)
                                }
                            }
                            listRiwayat.clear()
                            listRiwayat.addAll(newListRiwayat)
                            adapter.listRiwayat = listRiwayat
                            adapter.notifyDataSetChanged()
                        }
                    }
                    is ResourceData.Error ->{

                    }
                }
            }
        }
    }

    private fun deleteData(selectedData: RiwayatPenyakit?, position: Int?){
        selectedData?.id?.let {
            deleteDataViewModel.deleteData("riwayat_konsultasi_user", it.toString(), "id").observe(this){

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listRiwayat)
    }
}
package com.example.ta_hendryansha.view.admin.hasilKonsultasi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.adapter.kelola_data.AdminAdapter
import com.example.ta_hendryansha.adapter.kelola_data.HasilKonsultasiAdapter
import com.example.ta_hendryansha.adapter.kelola_data.RuleFcAdapter
import com.example.ta_hendryansha.adapter.kelola_data.UserAdapter
import com.example.ta_hendryansha.databinding.ActivityListAdminBinding
import com.example.ta_hendryansha.databinding.ActivityListHasilKonsultasiBinding
import com.example.ta_hendryansha.entity.Admin
import com.example.ta_hendryansha.entity.Gejala
import com.example.ta_hendryansha.entity.HasilKonulstasi
import com.example.ta_hendryansha.entity.RuleFc
import com.example.ta_hendryansha.entity.User
import com.example.ta_hendryansha.model.deleteDataModel.DeleteDataViewModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.model.riwayatHasilKonsultasi.RiwayatKonsultasiViewModel
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.admin.kelola_user.AddUpdateUserActivity
import com.example.ta_hendryansha.view.admin.kelola_user.ListUserActivity
import com.google.android.material.snackbar.Snackbar

class ListHasilKonsultasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListHasilKonsultasiBinding
    private val riwayatKonsultasiViewModel: RiwayatKonsultasiViewModel by viewModels {
        UserModelFactory()
    }
    private val deleteDataViewModel: DeleteDataViewModel by viewModels {
        UserModelFactory()
    }

    private lateinit var adapter: HasilKonsultasiAdapter
    private val listHasilKonulstasi = ArrayList<HasilKonulstasi>()
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListHasilKonsultasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvHasilKonsultasi.layoutManager = LinearLayoutManager(this)
        binding.rvHasilKonsultasi.setHasFixedSize(true)

        adapter = HasilKonsultasiAdapter(object : HasilKonsultasiAdapter.OnItemClickCallback{
            override fun onItemClicked(hasilKonulstasiSelected: HasilKonulstasi?, position: Int?) {
                val intent = Intent(this@ListHasilKonsultasiActivity, AddUpdateHasilKonsultasiActivity::class.java)
                intent.putExtra(AddUpdateHasilKonsultasiActivity.EXTRA_HASIl_KONSULTASI, hasilKonulstasiSelected)
                intent.putExtra(AddUpdateHasilKonsultasiActivity.EXTRA_POSITION, position)
                startActivity(intent)
            }
        })

        adapter.setOnDeleteClickCallback(object : HasilKonsultasiAdapter.OnDeleteClickCallback{
            override fun onDeleteClicked(selectedHasilKonulstasi: HasilKonulstasi?, position: Int?) {
                deleteData(selectedHasilKonulstasi, position)
                if (position != null) {
                    adapter.removeItem(position)
                }
                Snackbar.make(binding.rvHasilKonsultasi, "Satu item berhasil dihapus", Snackbar.LENGTH_SHORT).show()
            }
        })

        binding.rvHasilKonsultasi.adapter = adapter

        if (savedInstanceState == null) {
            loadDataRiwayatKonsultasi()
        } else {
            val list = savedInstanceState.getParcelableArrayList<HasilKonulstasi>(EXTRA_STATE)
            if (list != null) {
                adapter.listHasilKonsultasi = list
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadDataRiwayatKonsultasi(){
        riwayatKonsultasiViewModel.getRiwayatHasilKonsultasi().observe(this){
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{

                    }
                    is ResourceData.Success ->{
                        val data = it.data
                        if(data?.error == true){

                        }else{
                            if(data != null){
                                for(dataResult in data.data){
                                    val dataRiwayatKonsultasi = HasilKonulstasi(
                                        dataResult.id,
                                        dataResult.nikDokter,
                                        dataResult.namaDokter,
                                        dataResult.noBpjs,
                                        dataResult.noKartuBerobat,
                                        dataResult.namaPasien,
                                        dataResult.hasilDiagnosa,
                                        dataResult.waktu,
                                        dataResult.umur,
                                        dataResult.jenisKelamin,
                                        dataResult.pengobatan,
                                        dataResult.statusKonultasi
                                    )
                                    listHasilKonulstasi.add(dataRiwayatKonsultasi)
                                }
                                adapter.listHasilKonsultasi = listHasilKonulstasi
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                    is ResourceData.Error ->{

                    }
                }
            }
        }
    }

    private fun deleteData(selectedData: HasilKonulstasi?, position: Int?){
        selectedData?.id?.let {
            deleteDataViewModel.deleteData("riwayat_hasil_konsultasi", it.toString(), "id").observe(this){

            }
        }
    }
}
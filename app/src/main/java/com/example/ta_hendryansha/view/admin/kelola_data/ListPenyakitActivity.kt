package com.example.ta_hendryansha.view.admin.kelola_data

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.adapter.kelola_data.GejalaAdapter
import com.example.ta_hendryansha.adapter.kelola_data.PenyakitAdapter
import com.example.ta_hendryansha.databinding.ActivityListPenyakitBinding
import com.example.ta_hendryansha.entity.Gejala
import com.example.ta_hendryansha.entity.Penyakit
import com.example.ta_hendryansha.model.deleteDataModel.DeleteDataViewModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData
import com.google.android.material.snackbar.Snackbar

class ListPenyakitActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityListPenyakitBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }
    private val deleteDataViewModel: DeleteDataViewModel by viewModels {
        UserModelFactory()
    }

    private lateinit var adapter: PenyakitAdapter
    private val listPenyakit = ArrayList<Penyakit>()
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.data != null){

            when(result.resultCode){
                AddUpdatePenyakitActivity.RESULT_ADD -> {
                    val penyakit =
                        result.data?.getParcelableExtra<Penyakit>(AddUpdatePenyakitActivity.EXTRA_PENYAKIT) as Penyakit
                    adapter.addItem(penyakit)
                    binding.rvPenyakit.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                AddUpdatePenyakitActivity.RESULT_UPDATE -> {
                    val penyakit =
                        result.data?.getParcelableExtra<Penyakit>(AddUpdatePenyakitActivity.EXTRA_PENYAKIT) as Penyakit
                    val position =
                        result?.data?.getIntExtra(AddUpdatePenyakitActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, penyakit)
                    binding.rvPenyakit.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPenyakitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPenyakit.layoutManager = LinearLayoutManager(this)
        binding.rvPenyakit.setHasFixedSize(true)


        adapter = PenyakitAdapter(object : PenyakitAdapter.OnItemClickCallback{
            override fun onItemClicked(selectedPenyakit: Penyakit?, position: Int?) {
                val intent = Intent(this@ListPenyakitActivity, AddUpdatePenyakitActivity::class.java)
                intent.putExtra(AddUpdatePenyakitActivity.EXTRA_PENYAKIT, selectedPenyakit)
                intent.putExtra(AddUpdatePenyakitActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })

        adapter.setOnDeleteClickCallback(object : PenyakitAdapter.OnDeleteClickCallback{
            override fun onDeleteClicked(selectedPenyakit: Penyakit?, position: Int?) {
                deleteData(selectedPenyakit, position)
                if (position != null) {
                    adapter.removeItem(position)
                }
                showSnackbarMessage("Satu item berhasil dihapus")
            }
        })

        binding.rvPenyakit.adapter = adapter

        if (savedInstanceState == null) {
            loadDataPenyakit()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Penyakit>(EXTRA_STATE)
            if (list != null) {
                adapter.listPenyakit = list
            }
        }
        binding.add.setOnClickListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadDataPenyakit(){
        kelolaDataViewModel.getDataPenyakit().observe(this){
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{

                    }
                    is ResourceData.Success ->{
                        val data = it.data
                        if(data?.error == true){

                        }else{
                            if (data != null) {
                                for(dataResult in data.data){
                                    val dataPenyakit = Penyakit(dataResult.namaPenyakit, dataResult.kodePenyakit)
                                    listPenyakit.add(dataPenyakit)
                                }
                                adapter.listPenyakit = listPenyakit
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

    private fun deleteData(selectedData: Penyakit?, position: Int?){
        selectedData?.kodePenyakit?.let {
            deleteDataViewModel.deleteData("penyakit", it, "kode_penyakit").observe(this){

            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvPenyakit, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.add ->{
                val intent = Intent(this@ListPenyakitActivity, AddUpdatePenyakitActivity::class.java)
                resultLauncher.launch(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listPenyakit)
    }
}
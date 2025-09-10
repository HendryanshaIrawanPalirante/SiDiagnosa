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
import com.example.ta_hendryansha.databinding.ActivityListGejalaBinding
import com.example.ta_hendryansha.entity.Gejala
import com.example.ta_hendryansha.model.deleteDataModel.DeleteDataViewModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData
import com.google.android.material.snackbar.Snackbar

class ListGejalaActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var binding: ActivityListGejalaBinding
    private lateinit var adapter: GejalaAdapter
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }
    private val deleteDataViewModel: DeleteDataViewModel by viewModels {
        UserModelFactory()
    }
    private val listGejala = ArrayList<Gejala>()

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.data != null){

            when(result.resultCode){
                AddUpdateGejalaActivity.RESULT_ADD -> {
                    val gejala =
                        result.data?.getParcelableExtra<Gejala>(AddUpdateGejalaActivity.EXTRA_GEJALA) as Gejala
                    adapter.addItem(gejala)
                    binding.rvGejala.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                AddUpdateGejalaActivity.RESULT_UPDATE -> {
                    val gejala =
                        result.data?.getParcelableExtra<Gejala>(AddUpdateGejalaActivity.EXTRA_GEJALA) as Gejala
                    val position =
                        result?.data?.getIntExtra(AddUpdateGejalaActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, gejala)
                    binding.rvGejala.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
                AddUpdateGejalaActivity.RESULT_DELETE -> {
                    val position =
                        result?.data?.getIntExtra(AddUpdateGejalaActivity.EXTRA_POSITION, 0) as Int
                    adapter.removeItem(position)
                    showSnackbarMessage("Satu item berhasil dihapus")
                }
            }

        }
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityListGejalaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvGejala.layoutManager = LinearLayoutManager(this)
        binding.rvGejala.setHasFixedSize(true)

        adapter = GejalaAdapter(object : GejalaAdapter.OnItemClickCallback{
            override fun onItemClicked(selectedGejala: Gejala?, position: Int?) {
                val intent = Intent(this@ListGejalaActivity, AddUpdateGejalaActivity::class.java)
                intent.putExtra(AddUpdateGejalaActivity.EXTRA_GEJALA, selectedGejala)
                intent.putExtra(AddUpdateGejalaActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })

        adapter.setOnDeleteClickCallback(object : GejalaAdapter.OnDeleteClickCallback{
            override fun onDeleteClicked(selectedGejala: Gejala?, position: Int?) {
                deleteData(selectedGejala, position)
                if (position != null) {
                    adapter.removeItem(position)
                }
                showSnackbarMessage("Satu item berhasil dihapus")
            }
        })

        binding.rvGejala.adapter = adapter

        if (savedInstanceState == null) {
            loadDataGejala()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Gejala>(EXTRA_STATE)
            if (list != null) {
                adapter.listGejala = list
            }
        }
        binding.add.setOnClickListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadDataGejala(){
        kelolaDataViewModel.getDataGejala().observe(this){
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
                                    val gejalaData = Gejala(dataResult.namaGejala, dataResult.kodeGejala)
                                    listGejala.add(gejalaData)
                                }
                            }
                            adapter.listGejala = listGejala
                            adapter.notifyDataSetChanged()
                        }
                    }
                    is ResourceData.Error ->{

                    }
                }
            }
        }
    }

    private fun deleteData(selectedData: Gejala?, position: Int?){
        selectedData?.kodeGejala?.let {
            deleteDataViewModel.deleteData("gejala", it, "kode_gejala").observe(this){

            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvGejala, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.add ->{
                val intent = Intent(this@ListGejalaActivity, AddUpdateGejalaActivity::class.java)
                resultLauncher.launch(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listGejala)
    }
}
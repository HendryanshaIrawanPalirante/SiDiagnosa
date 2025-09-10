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
import com.example.ta_hendryansha.adapter.kelola_data.RuleCfAdapter
import com.example.ta_hendryansha.databinding.ActivityListRuleCfBinding
import com.example.ta_hendryansha.entity.Gejala
import com.example.ta_hendryansha.entity.RuleCf
import com.example.ta_hendryansha.model.deleteDataModel.DeleteDataViewModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData
import com.google.android.material.snackbar.Snackbar

class ListRuleCfActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityListRuleCfBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }
    private val deleteDataViewModel: DeleteDataViewModel by viewModels {
        UserModelFactory()
    }

    private lateinit var adapter: RuleCfAdapter
    private val listRuleCf = ArrayList<RuleCf>()
    companion object{
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.data != null){

            when(result.resultCode){
                AddUpdateRuleCfActivity.RESULT_ADD -> {
                    val rule =
                        result.data?.getParcelableExtra<RuleCf>(AddUpdateRuleCfActivity.EXTRA_RULE_CF) as RuleCf
                    adapter.addItem(rule)
                    binding.rvRuleCf.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                AddUpdateRuleCfActivity.RESULT_UPDATE -> {
                    val rule =
                        result.data?.getParcelableExtra<RuleCf>(AddUpdateRuleCfActivity.EXTRA_RULE_CF) as RuleCf
                    val position =
                        result?.data?.getIntExtra(AddUpdateRuleCfActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, rule)
                    binding.rvRuleCf.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListRuleCfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRuleCf.layoutManager = LinearLayoutManager(this)
        binding.rvRuleCf.setHasFixedSize(true)

        adapter = RuleCfAdapter(object : RuleCfAdapter.OnItemClickCallback{
            override fun onItemClicked(selectedRuleCf: RuleCf?, position: Int?) {
                val intent = Intent(this@ListRuleCfActivity, AddUpdateRuleCfActivity::class.java)
                intent.putExtra(AddUpdateRuleCfActivity.EXTRA_RULE_CF, selectedRuleCf)
                intent.putExtra(AddUpdateRuleCfActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })

        adapter.setOnDeleteClickCallback(object : RuleCfAdapter.OnDeleteClickCallback{
            override fun onDeleteClicked(selectedRuleCf: RuleCf?, position: Int?) {
                deleteData(selectedRuleCf, position)
                if (position != null) {
                    adapter.removeItem(position)
                }
                showSnackbarMessage("Satu item berhasil dihapus")
            }
        })

        binding.rvRuleCf.adapter = adapter

        if (savedInstanceState == null) {
            loadDataRuleCf()
        } else {
            val list = savedInstanceState.getParcelableArrayList<RuleCf>(EXTRA_STATE)
            if (list != null) {
                adapter.listRuleCf = list
            }
        }
        binding.add.setOnClickListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadDataRuleCf(){
        kelolaDataViewModel.getDataRuleCf().observe(this){
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
                                    val dataRuleCf = RuleCf(dataResult.idRule, dataResult.kodePenyakit, dataResult.kodeGejala, dataResult.nilaiCf, dataResult.intensitas, dataResult.waktu)
                                    listRuleCf.add(dataRuleCf)
                                }
                                adapter.listRuleCf = listRuleCf
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

    private fun deleteData(selectedData: RuleCf?, position: Int?){
        selectedData?.id?.let {
            deleteDataViewModel.deleteData("rule_cf", it.toString(), "id_rule").observe(this){

            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvRuleCf, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.add ->{
                val intent = Intent(this@ListRuleCfActivity, AddUpdateRuleCfActivity::class.java)
                resultLauncher.launch(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listRuleCf)
    }
}
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
import com.example.ta_hendryansha.adapter.kelola_data.RuleFcAdapter
import com.example.ta_hendryansha.databinding.ActivityListRuleFcBinding
import com.example.ta_hendryansha.entity.Gejala
import com.example.ta_hendryansha.entity.RuleFc
import com.example.ta_hendryansha.model.deleteDataModel.DeleteDataViewModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData
import com.google.android.material.snackbar.Snackbar

class ListRuleFcActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityListRuleFcBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }
    private val deleteDataViewModel: DeleteDataViewModel by viewModels {
        UserModelFactory()
    }

    private lateinit var adapter: RuleFcAdapter
    private val listRuleFc = ArrayList<RuleFc>()
    companion object{
        private const val EXTRA_STATE = "EXTRA_STATE"
    }


    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.data != null){

            when(result.resultCode){
                AddUpdateGejalaActivity.RESULT_ADD -> {
                    val rule =
                        result.data?.getParcelableExtra<RuleFc>(AddUpdateRuleFcActivity.EXTRA_RULE_FC) as RuleFc
                    adapter.addItem(rule)
                    binding.rvRuleFc.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                AddUpdateGejalaActivity.RESULT_UPDATE -> {
                    val rule =
                        result.data?.getParcelableExtra<RuleFc>(AddUpdateRuleFcActivity.EXTRA_RULE_FC) as RuleFc
                    val position =
                        result?.data?.getIntExtra(AddUpdateRuleFcActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, rule)
                    binding.rvRuleFc.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListRuleFcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRuleFc.layoutManager = LinearLayoutManager(this)
        binding.rvRuleFc.setHasFixedSize(true)

        adapter = RuleFcAdapter(object : RuleFcAdapter.OnItemClickCallback{
            override fun onItemClicked(selectedRuleFc: RuleFc?, position: Int?) {
                val intent = Intent(this@ListRuleFcActivity, AddUpdateRuleFcActivity::class.java)
                intent.putExtra(AddUpdateRuleFcActivity.EXTRA_RULE_FC, selectedRuleFc)
                intent.putExtra(AddUpdateRuleFcActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })

        adapter.setOnDeleteClickCallback(object : RuleFcAdapter.OnDeleteClickCallback{
            override fun onDeleteClicked(selectedRuleFc: RuleFc?, position: Int?) {
                deleteData(selectedRuleFc, position)
                if (position != null) {
                    adapter.removeItem(position)
                }
                showSnackbarMessage("Satu item berhasil dihapus")
            }
        })

        binding.rvRuleFc.adapter = adapter

        if (savedInstanceState == null) {
            loadDataRuleFc()
        } else {
            val list = savedInstanceState.getParcelableArrayList<RuleFc>(EXTRA_STATE)
            if (list != null) {
                adapter.listRuleFc = list
            }
        }
        binding.add.setOnClickListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadDataRuleFc(){
        kelolaDataViewModel.getDataRuleFc().observe(this){
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
                                    val dataRuleFc = RuleFc(dataResult.idRule, dataResult.idKeputusan, dataResult.ya, dataResult.tidak)
                                    listRuleFc.add(dataRuleFc)
                                }
                                adapter.listRuleFc = listRuleFc
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

    private fun deleteData(selectedData: RuleFc?, position: Int?){
        selectedData?.id?.let {
            deleteDataViewModel.deleteData("rule_fc", it.toString(), "id").observe(this){

            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvRuleFc, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.add ->{
                val intent = Intent(this@ListRuleFcActivity, AddUpdateRuleFcActivity::class.java)
                resultLauncher.launch(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listRuleFc)
    }
}
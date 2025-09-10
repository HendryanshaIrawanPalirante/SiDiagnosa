package com.example.ta_hendryansha.view.admin.kelola_user

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
import com.example.ta_hendryansha.adapter.kelola_data.AdminAdapter
import com.example.ta_hendryansha.databinding.ActivityListAdminBinding
import com.example.ta_hendryansha.entity.Admin
import com.example.ta_hendryansha.model.deleteDataModel.DeleteDataViewModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData
import com.google.android.material.snackbar.Snackbar

class ListAdminActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityListAdminBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }
    private val deleteDataViewModel: DeleteDataViewModel by viewModels {
        UserModelFactory()
    }
    private lateinit var adapter: AdminAdapter
    private val listAdmin = ArrayList<Admin>()
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.data != null){

            when(result.resultCode){
                AddUpdateAdminActivity.RESULT_ADD -> {
                    val admin =
                        result.data?.getParcelableExtra<Admin>(AddUpdateAdminActivity.EXTRA_ADMIN) as Admin
                    adapter.addItem(admin)
                    binding.rvAdmin.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                AddUpdateAdminActivity.RESULT_UPDATE -> {
                    val admin =
                        result.data?.getParcelableExtra<Admin>(AddUpdateAdminActivity.EXTRA_ADMIN) as Admin
                    val position =
                        result?.data?.getIntExtra(AddUpdateAdminActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, admin)
                    binding.rvAdmin.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvAdmin.layoutManager = LinearLayoutManager(this)
        binding.rvAdmin.setHasFixedSize(true)

        adapter = AdminAdapter(object : AdminAdapter.OnItemClickCallback{
            override fun onItemClicked(selectedAdmin: Admin?, position: Int?) {
                val intent = Intent(this@ListAdminActivity, AddUpdateAdminActivity::class.java)
                intent.putExtra(AddUpdateAdminActivity.EXTRA_ADMIN, selectedAdmin)
                intent.putExtra(AddUpdateAdminActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })

        adapter.setOnDeleteClickCallback(object : AdminAdapter.OnDeleteClickCallback{
            override fun onDeleteClicked(selectedAdmin: Admin?, position: Int?) {
                deleteData(selectedAdmin, position)
                if (position != null) {
                    adapter.removeItem(position)
                }
                showSnackbarMessage("Satu item berhasil dihapus")
            }
        })

        binding.rvAdmin.adapter = adapter

        if (savedInstanceState == null) {
            loadDataAdmin()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Admin>(EXTRA_STATE)
            if (list != null) {
                adapter.listAdmin = list
            }
        }

        binding.btnAdd.setOnClickListener(this)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadDataAdmin(){
        kelolaDataViewModel.getDataAdmin().observe(this){
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
                                    val gejalaData = Admin(
                                        dataResult.email,
                                        dataResult.password,
                                        dataResult.namaLengkap,
                                        dataResult.umur,
                                        dataResult.jenisKelamin,
                                        dataResult.nik,
                                        dataResult.pekerjaan,
                                        dataResult.tipeAkun
                                    )
                                    listAdmin.add(gejalaData)
                                }
                            }
                            adapter.listAdmin = listAdmin
                            adapter.notifyDataSetChanged()
                        }
                    }
                    is ResourceData.Error ->{

                    }
                }
            }
        }
    }

    private fun deleteData(selectedData: Admin?, position: Int?){
        selectedData?.email?.let {
            deleteDataViewModel.deleteData("akun", it, "email").observe(this){

            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvAdmin, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listAdmin)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnAdd ->{
                val intent = Intent(this@ListAdminActivity, AddUpdateAdminActivity::class.java)
                resultLauncher.launch(intent)
            }
        }
    }

}
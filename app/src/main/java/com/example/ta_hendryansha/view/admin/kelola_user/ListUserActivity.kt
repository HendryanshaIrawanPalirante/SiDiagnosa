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
import com.example.ta_hendryansha.adapter.kelola_data.UserAdapter
import com.example.ta_hendryansha.databinding.ActivityListUserBinding
import com.example.ta_hendryansha.entity.User
import com.example.ta_hendryansha.model.deleteDataModel.DeleteDataViewModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData
import com.google.android.material.snackbar.Snackbar

class ListUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityListUserBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }
    private val deleteDataViewModel: DeleteDataViewModel by viewModels {
        UserModelFactory()
    }
    private lateinit var adapter: UserAdapter
    private val listUser = ArrayList<User>()
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.data != null){

            when(result.resultCode){
                AddUpdateUserActivity.RESULT_ADD -> {
                    val user =
                        result.data?.getParcelableExtra<User>(AddUpdateUserActivity.EXTRA_USER) as User
                    adapter.addItem(user)
                    binding.rvUser.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                AddUpdateUserActivity.RESULT_UPDATE -> {
                    val user =
                        result.data?.getParcelableExtra<User>(AddUpdateUserActivity.EXTRA_USER) as User
                    val position =
                        result?.data?.getIntExtra(AddUpdateUserActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, user)
                    binding.rvUser.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)

        adapter = UserAdapter(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(selectedUser: User?, position: Int?) {
                val intent = Intent(this@ListUserActivity, AddUpdateUserActivity::class.java)
                intent.putExtra(AddUpdateUserActivity.EXTRA_USER, selectedUser)
                intent.putExtra(AddUpdateUserActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })

        adapter.setOnDeleteClickCallback(object : UserAdapter.OnDeleteClickCallback{
            override fun onDeleteClicked(selectedUser: User?, position: Int?) {
                deleteData(selectedUser, position)
                if (position != null) {
                    adapter.removeItem(position)
                }
                showSnackbarMessage("Satu item berhasil dihapus")
            }
        })

        binding.rvUser.adapter = adapter

        if (savedInstanceState == null) {
            loadDataUser()
        } else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (list != null) {
                adapter.listUser = list
            }
        }

        binding.btnAdd.setOnClickListener(this)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadDataUser(){
        kelolaDataViewModel.getDataUser().observe(this){
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
                                    val gejalaUser = User(
                                        dataResult.email,
                                        dataResult.password,
                                        dataResult.namaLengkap,
                                        dataResult.umur,
                                        dataResult.jenisKelamin,
                                        dataResult.noKartuBerobat,
                                        dataResult.noBpjs
                                    )
                                    listUser.add(gejalaUser)
                                }
                            }
                            adapter.listUser = listUser
                            adapter.notifyDataSetChanged()
                        }
                    }
                    is ResourceData.Error ->{

                    }
                }
            }
        }
    }

    private fun deleteData(selectedData: User?, position: Int?){
        selectedData?.email?.let {
            deleteDataViewModel.deleteData("akun", it, "email").observe(this){

            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvUser, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listUser)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnAdd ->{
                val intent = Intent(this@ListUserActivity, AddUpdateUserActivity::class.java)
                resultLauncher.launch(intent)
            }
        }
    }

}
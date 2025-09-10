package com.example.ta_hendryansha.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.adapter.menu.MenuAdapter
import com.example.ta_hendryansha.data.menu.DataMenu
import com.example.ta_hendryansha.databinding.ActivityMainBinding
import com.example.ta_hendryansha.model.MenuModel
import com.example.ta_hendryansha.model.UserViewModel
import com.example.ta_hendryansha.model.biodataModel.BiodataUserViewModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.factoryModel.ViewModelFactory
import com.example.ta_hendryansha.preference.UserPreference
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.admin.AdminActivity
import com.example.ta_hendryansha.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var rvMenu: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private var listMenu = ArrayList<MenuModel>()
    private lateinit var binding: ActivityMainBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val mainViewModel: UserViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private val biodataUserViewModel: BiodataUserViewModel by viewModels {
        UserModelFactory()
    }
    private lateinit var nameBar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val includeAppBar: View = binding.appBarLayout
        nameBar = includeAppBar.findViewById(R.id.user_login)

        val logOut: View = includeAppBar.findViewById(R.id.btn_log_out)

        setupViewModel()
        logOut.setOnClickListener(this)
    }

    private fun setupViewModel(){
        mainViewModel.getUser().observe(this){user ->
            if(user.isLogin && user.tipeAkun == "user"){
                setData(user.email)
                setInitLayout()
                setMenu()
            } else if(user.isLogin && (user.tipeAkun == "admin")){
                startActivity(Intent(this, AdminActivity::class.java))
            } else{
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun setData(email: String){
        biodataUserViewModel.getUser(email).observe(this){
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{

                    }
                    is ResourceData.Success ->{
                        val data = it.data?.data
                        data?.let {dataUser ->
                            binding.tvNama.text = dataUser.namaLengkap
                            binding.tvJenisKelamin.text = dataUser.jenisKelamin
                            binding.tvUmur.text = dataUser.umur.toString()
                            binding.tvNoKartuBerobat.text = dataUser.noKartuBerobat
                            binding.tvNoBpjs.text = dataUser.noBpjs.toString()
                            nameBar.text = dataUser.namaLengkap
                        }
                    }
                    is ResourceData.Error ->{

                    }
                }
            }
        }
    }

    private fun setInitLayout(){
        rvMenu = findViewById(R.id.rvMenu)
        rvMenu.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        rvMenu.setHasFixedSize(true)
    }

    private fun setMenu(){
        listMenu.addAll(DataMenu.dataMenu())
        menuAdapter = MenuAdapter(listMenu)
        rvMenu.adapter = menuAdapter
    }

    private fun setLogOut(){
        mainViewModel.logout()
        startActivity(Intent(this, WelcomeActivity::class.java))
        finishAffinity()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_log_out ->{
                setLogOut()
            }
        }
    }

}
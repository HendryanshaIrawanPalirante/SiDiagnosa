package com.example.ta_hendryansha.view.admin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.model.UserViewModel
import com.example.ta_hendryansha.model.factoryModel.ViewModelFactory
import com.example.ta_hendryansha.preference.UserPreference
import com.example.ta_hendryansha.view.welcome.WelcomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminActivity : AppCompatActivity(), View.OnClickListener {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val mainViewModel: UserViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    private fun setLogOut(){
        mainViewModel.logout()
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_log_out ->{
                setLogOut()
            }
        }
    }
}
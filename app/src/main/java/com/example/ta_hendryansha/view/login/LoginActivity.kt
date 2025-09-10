package com.example.ta_hendryansha.view.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityLoginBinding
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.UserViewModel
import com.example.ta_hendryansha.model.factoryModel.ViewModelFactory
import com.example.ta_hendryansha.model.loginModel.LoginViewModel
import com.example.ta_hendryansha.model.userModel.UserModel
import com.example.ta_hendryansha.preference.UserPreference
import com.example.ta_hendryansha.remote.response.LoginResponse
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.main.MainActivity
import com.google.gson.Gson
import retrofit2.HttpException

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val loginViewModel: LoginViewModel by viewModels{
        UserModelFactory()
    }
    private lateinit var mainViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        binding.loginButton.setOnClickListener(this)

    }

    private fun setupViewModel(){
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[UserViewModel::class.java]
    }

    private fun login(){
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        Log.d("LoginActivity", "Berhasil")
        loginViewModel.login(email, password).observe(this){ it ->
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{
                    }
                    is ResourceData.Success ->{
                        val dataUser = it.data
                        if(dataUser?.error == true){
                            Toast.makeText(
                                this,
                                dataUser.pesan,
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            Log.d("LoginActivity", "Berhasil")
                            AlertDialog.Builder(this).apply {
                                setTitle("Berhasil Login")
                                setMessage("Selamat Datatang")
                                setPositiveButton("Ok!"){_, _ ->
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                                val emailLogin = dataUser?.data?.email
                                val tipeAkun = dataUser?.data?.tipeAkun

                                emailLogin?.let { it1 -> tipeAkun?.let {it2 -> UserModel(it1, it2,true) } }
                                    ?.let { it3 -> mainViewModel.saveUser(it3) }
                            }
                        }
                    }
                    is ResourceData.Error ->{
                        var message = "Anda Gagal Login"
                        try{
                            Gson().fromJson(
                                (it.message as HttpException).response()?.errorBody()?.toString(), LoginResponse::class.java
                            ).pesan.let {
                                message = it
                            }
                        }catch (e: Exception){
                            Toast.makeText(
                                this@LoginActivity,
                                e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        Toast.makeText(
                            this@LoginActivity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.loginButton ->{
                login()
            }
        }
    }
}
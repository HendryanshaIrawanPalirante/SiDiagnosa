package com.example.ta_hendryansha.view.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivitySignupBinding
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.registerModel.RegisterViewModel
import com.example.ta_hendryansha.remote.response.RegisterResponse
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.login.LoginActivity
import com.google.gson.Gson
import retrofit2.HttpException

class SignupActivity : AppCompatActivity(), View.OnClickListener {

    private val registerViewModel: RegisterViewModel by viewModels {
        UserModelFactory()
    }
    private val strGender = arrayOf("Laki-Laki", "Perempuan")
    private lateinit var sGender: String
    private var countAge = 0
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Memanggil function spinner
        setSpinnerAdapter()

        //Membuat on clik listener
        binding.imageAdd1.setOnClickListener(this)
        binding.imageMinus1.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)

    }

    private fun setSpinnerAdapter(){
        val adapterGender = ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, strGender)
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spGender.adapter = adapterGender

        binding.spGender.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    sGender = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.imageMinus1 -> {
                if(countAge > 0){
                    countAge -= 1
                    binding.tvAge.text = countAge.toString()
                }
            }
            R.id.imageAdd1 -> {
                countAge += 1
                binding.tvAge.text = countAge.toString()
            }
            R.id.btnRegister -> {
                register()
            }
        }
    }

    private fun register(){
        val noBpjs = Integer.parseInt(binding.inputNoBpjs.text.toString())
        val email = binding.inputEmail.text.toString().trim()
        val password = binding.inputPassword.text.toString().trim()
        val namaLengkap = binding.inputNama.text.toString().trim()
        val umur = Integer.parseInt(binding.tvAge.text.toString())
        val jenisKelamin = sGender
        val noKartuBerobat = binding.inputNoKartu.text.toString().trim()
        val tipeAkun = "user"

        registerViewModel.registerUser(
            noBpjs,
            email,
            password,
            namaLengkap,
            umur,
            jenisKelamin,
            noKartuBerobat,
            tipeAkun
        ).observe(this){
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{
                    }
                    is ResourceData.Success ->{
                        val data = it.data
                        if(data?.error == true){
                            Toast.makeText(
                                this,
                                data.pesan,
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            Toast.makeText(
                                this,
                                data?.pesan,
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    is ResourceData.Error ->{
                        var message = "Register Gagal"
                        try{
                            Gson().fromJson(
                                (it.message as HttpException).response()?.errorBody()?.string(), RegisterResponse::class.java
                            ).pesan.let {error ->
                                message = error
                            }
                        }catch (e: Exception){
                            Toast.makeText(
                                this@SignupActivity,
                                e.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        Toast.makeText(
                            this@SignupActivity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

}
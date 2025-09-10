package com.example.ta_hendryansha.view.user.biodataUser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityBiodataBinding
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.UserViewModel
import com.example.ta_hendryansha.model.factoryModel.ViewModelFactory
import com.example.ta_hendryansha.model.biodataModel.BiodataUserViewModel
import com.example.ta_hendryansha.model.userModel.UserModel
import com.example.ta_hendryansha.preference.UserPreference
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.main.MainActivity

class BiodataActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityBiodataBinding
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val userViewModel: UserViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(datastore))
    }
    private val biodataUserViewModel: BiodataUserViewModel by viewModels {
        UserModelFactory()
    }
    private lateinit var sGender: String
    private val strGender = arrayOf("Laki-Laki", "Perempuan")
    private var idEmail: String = ""
    private var idBpjs: Int = 0
    private var countAge = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiodataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel.getUser().observe(this){user ->
            biodataUserViewModel.getUser(user.email).observe(this){ dataUser ->
                if(dataUser != null){
                    when(dataUser){
                        is ResourceData.Loading ->{

                        }
                        is ResourceData.Success ->{
                            val data = dataUser.data
                            if(data?.error == true){

                            }else{
                                if(data != null){
                                    idEmail = data.data.email
                                    idBpjs = data.data.noBpjs
                                    updateData(
                                        data.data.noBpjs,
                                        data.data.email,
                                        data.data.password,
                                        data.data.namaLengkap,
                                        data.data.umur,
                                        data.data.jenisKelamin,
                                        data.data.noKartuBerobat
                                    )
                                }

                            }
                        }
                        is ResourceData.Error ->{

                        }
                    }
                }
            }
        }

        binding.imageAdd1.setOnClickListener(this)
        binding.imageMinus1.setOnClickListener(this)

    }

    private fun updateData(
        noBpjs: Int,
        email: String,
        password: String,
        namaLengkap: String,
        umur: Int,
        jenisKelamin: String,
        noKartuBerobat: String
    ){
        setSpinnerAdapter()

        binding.inputNoBpjs.setText(noBpjs.toString())
        binding.inputEmail.setText(email)
        binding.inputPassword.setText(password)
        binding.inputNama.setText(namaLengkap)
        binding.tvAge.text = umur.toString()
        binding.inputNoKartu.setText(noKartuBerobat)
        val genderIndex = strGender.indexOf(jenisKelamin)
        if (genderIndex != -1) {
            binding.spGender.setSelection(genderIndex)
        }

        binding.btnEdit.setOnClickListener(this)

    }

    fun setSpinnerAdapter(){
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
            R.id.btnEdit ->{
                editData()
            }
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
        }
    }

    fun editData(){
        val editNoBpjs = Integer.parseInt(binding.inputNoBpjs.text.toString())
        val editEmail = binding.inputEmail.text.toString().trim()
        val editPassword = binding.inputPassword.text.toString().trim()
        val editNama = binding.inputNama.text.toString().trim()
        val editUmur = Integer.parseInt(binding.tvAge.text.toString())
        val editJenisKelamin = sGender
        val editNoKartuBerobat = binding.inputNoKartu.text.toString().trim()
        val tipeAkun = "user"

        biodataUserViewModel.updateUser(idEmail, idBpjs, editNoBpjs, editEmail, editPassword, editNama, editUmur, editJenisKelamin, editNoKartuBerobat).
        observe(this){
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{
                    }
                    is ResourceData.Success ->{
                        val data = it.data
                        if(data?.error == true){
                            Toast.makeText(
                                this,
                                data.pesan ?: "Undah Data Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            val intent = Intent(this@BiodataActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            userViewModel.saveUser(UserModel(editEmail, tipeAkun, true))
                        }
                    }
                    is ResourceData.Error ->{
                    }
                }
            }
        }
    }

}
package com.example.ta_hendryansha.view.admin.kelola_user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityAddUpdateUserBinding
import com.example.ta_hendryansha.entity.User
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.admin.kelola_data.AddUpdateRuleCfActivity

class AddUpdateUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddUpdateUserBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }

    private var isEdit = false
    private var user: User? = null
    private var position: Int = 0
    private var btnTitle: String? = null
    private var idEmail: String = ""
    private var idBpjs: Int = 0
    private lateinit var sGender: String
    private val strGender = arrayOf("Laki-Laki", "Perempuan")
    private var countAge = 0
    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra(EXTRA_USER)
        idEmail = user?.email.toString()
        idBpjs = user?.noBpjs ?: 0

        if(user != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        }else{
            user = User()
        }
        setSpinnerAdapter()

        if(isEdit){
            btnTitle = "Edit"
            user?.let {
                binding.inputEmail.setText(it.email)
                binding.inputPassword.setText(it.password)
                binding.inputNama.setText(it.namaLengkap)
                binding.tvAge.text = it.umur.toString()
                val genderIndex = strGender.indexOf(it.jenisKelamin)
                if (genderIndex != -1) {
                    binding.spGender.setSelection(genderIndex)
                }
                binding.inputNoKartu.setText(it.noKartuBerobat)
                binding.inputNoBpjs.setText(it.noBpjs.toString())
            }
        }else{
            btnTitle = "Simpan"
        }
        binding.btnSubmit.text = btnTitle
        binding.imageAdd1.setOnClickListener(this)
        binding.imageMinus1.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)

    }

    private fun addUpdateUser(){
        val email = binding.inputEmail.text.toString().trim()
        val password = binding.inputPassword.text.toString().trim()
        val namaLengkap = binding.inputNama.text.toString().trim()
        val umur = Integer.parseInt(binding.tvAge.text.toString())
        val jenisKelamin = sGender
        val noBpjs = Integer.parseInt(binding.inputNoBpjs.text.toString().trim())
        val kartuBerobat = binding.inputNoKartu.text.toString().trim()
        val tipeAkun = "user"

        user?.email = email
        user?.password = password
        user?.namaLengkap = namaLengkap
        user?.umur = umur
        user?.jenisKelamin = jenisKelamin
        user?.noBpjs = noBpjs
        user?.noKartuBerobat = kartuBerobat

        val intent = Intent()
        intent.putExtra(EXTRA_USER, user)
        intent.putExtra(EXTRA_POSITION, position)

        if(isEdit){
            kelolaDataViewModel.updateUser(
                idEmail,
                idBpjs,
                email,
                password,
                tipeAkun,
                noBpjs,
                namaLengkap,
                umur,
                jenisKelamin,
                kartuBerobat
            ).observe(this){
                if(it != null){
                    when(it){
                        is ResourceData.Loading ->{

                        }
                        is ResourceData.Success ->{
                            if(it.data?.error == false){
                                setResult(AddUpdateRuleCfActivity.RESULT_UPDATE, intent)
                                finish()
                            }
                        }
                        is ResourceData.Error ->{

                        }
                    }
                }
            }
        }else{
            kelolaDataViewModel.insertUser(email, password, tipeAkun, noBpjs, namaLengkap, umur, jenisKelamin, kartuBerobat)
                .observe(this){
                    if(it != null){
                        when(it){
                            is ResourceData.Loading ->{

                            }
                            is ResourceData.Success ->{
                                if(it.data?.error == false){
                                    setResult(AddUpdateRuleCfActivity.RESULT_ADD, intent)
                                    finish()
                                }
                            }
                            is ResourceData.Error ->{

                            }
                        }
                    }
                }
        }

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
            R.id.btnSubmit ->{
                addUpdateUser()
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
}
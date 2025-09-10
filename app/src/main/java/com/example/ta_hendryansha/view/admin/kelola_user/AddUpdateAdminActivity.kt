package com.example.ta_hendryansha.view.admin.kelola_user

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityAddUpdateAdminBinding
import com.example.ta_hendryansha.entity.Admin
import com.example.ta_hendryansha.model.UserViewModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.factoryModel.ViewModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.preference.UserPreference
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.admin.kelola_data.AddUpdateRuleCfActivity
import com.example.ta_hendryansha.view.welcome.WelcomeActivity

class AddUpdateAdminActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddUpdateAdminBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val userViewModel: UserViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(datastore))
    }

    private var isEdit = false
    private var admin: Admin? = null
    private var position: Int = 0
    private var btnTitle: String? = null
    private var idEmail: String = ""
    private var idNik: Int = 0
    private lateinit var sGender: String
    private val strGender = arrayOf("Laki-Laki", "Perempuan")
    private var countAge = 0
    companion object {
        const val EXTRA_ADMIN = "extra_admin"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        admin = intent.getParcelableExtra(EXTRA_ADMIN)
        idEmail = admin?.email.toString()
        idNik = admin?.nik ?: 0

        if(admin != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        }else{
            admin = Admin()
        }
        setSpinnerAdapter()

        if(isEdit){
            btnTitle = "Edit"
            admin?.let {
                binding.inputEmail.setText(it.email)
                binding.inputPassword.setText(it.password)
                binding.inputNama.setText(it.namaLengkap)
                binding.tvAge.text = it.umur.toString()
                val genderIndex = strGender.indexOf(it.jenisKelamin)
                if (genderIndex != -1) {
                    binding.spGender.setSelection(genderIndex)
                }
                binding.inputNik.setText(it.nik.toString())
                binding.inputPekerjaan.setText(it.pekerjaan)
            }
        }else{
            btnTitle = "Simpan"
        }
        binding.btnSubmit.text = btnTitle
        binding.imageAdd1.setOnClickListener(this)
        binding.imageMinus1.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)

    }

    private fun addUpdateAdmin(){
        val email = binding.inputEmail.text.toString().trim()
        val password = binding.inputPassword.text.toString().trim()
        val namaLengkap = binding.inputNama.text.toString().trim()
        val umur = Integer.parseInt(binding.tvAge.text.toString())
        val jenisKelamin = sGender
        val nik = Integer.parseInt(binding.inputNik.text.toString().trim())
        val pekerjaan = binding.inputPekerjaan.text.toString().trim()
        val tipeAkun = "admin"

        admin?.email = email
        admin?.password = password
        admin?.namaLengkap = namaLengkap
        admin?.umur = umur
        admin?.jenisKelamin = jenisKelamin
        admin?.nik = nik
        admin?.pekerjaan = pekerjaan
        admin?.tipeAkun = tipeAkun

        val intent = Intent()
        intent.putExtra(EXTRA_ADMIN, admin)
        intent.putExtra(EXTRA_POSITION, position)

        if(isEdit){
            kelolaDataViewModel.updateAdmin(
                idEmail,
                idNik,
                email,
                password,
                tipeAkun,
                nik,
                namaLengkap,
                umur,
                jenisKelamin,
                pekerjaan
            ).observe(this){
                if(it != null){
                    when(it){
                        is ResourceData.Loading ->{

                        }
                        is ResourceData.Success ->{
                            if(it.data?.error == false){
                                userViewModel.getUser().observe(this){ dataLogin ->
                                    if((dataLogin.email != email) && (dataLogin.email == idEmail)){
                                        setLogOut()
                                    }else{
                                        setResult(AddUpdateRuleCfActivity.RESULT_UPDATE, intent)
                                        finish()
                                    }
                                }
                            }
                        }
                        is ResourceData.Error ->{

                        }
                    }
                }
            }
        }else{
            kelolaDataViewModel.insertAdmin(
                email, password, tipeAkun, nik, namaLengkap, umur, jenisKelamin, pekerjaan
            ).observe(this){
                kelolaDataViewModel.updateAdmin(
                    idEmail,
                    idNik,
                    email,
                    password,
                    tipeAkun,
                    nik,
                    namaLengkap,
                    umur,
                    jenisKelamin,
                    pekerjaan
                ).observe(this){
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

            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnSubmit ->{
                addUpdateAdmin()
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

    private fun setLogOut(){
        userViewModel.logout()
        startActivity(Intent(this, WelcomeActivity::class.java))
        finishAffinity()
    }
}
package com.example.ta_hendryansha.view.admin.kelola_data

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityAddUpdateGejalaBinding
import com.example.ta_hendryansha.entity.Gejala
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData

class AddUpdateGejalaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddUpdateGejalaBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }

    private var isEdit = false
    private var gejala: Gejala? = null
    private var position: Int = 0
    private var btnTitle: String? = null
    private var idGejala: String = ""
    companion object {
        const val EXTRA_GEJALA = "extra_gejala"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateGejalaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gejala = intent.getParcelableExtra(EXTRA_GEJALA)
        idGejala = gejala?.kodeGejala.toString()
        if(gejala != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        }else{
            gejala = Gejala()
        }

        if(isEdit){
            btnTitle = "Edit"
            gejala?.let {
                binding.inputNamaGejala.setText(it.namaGejala)
                binding.inputKodeGejala.setText(it.kodeGejala)
            }
        }else{
            btnTitle = "Simpan"
        }
        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener(this)

    }

    private fun addUpdateData(){
        val kodeGejala = binding.inputKodeGejala.text.toString().trim()
        val namaGejala = binding.inputNamaGejala.text.toString().trim()

        gejala?.kodeGejala = kodeGejala
        gejala?.namaGejala = namaGejala

        val intent = Intent()
        intent.putExtra(EXTRA_GEJALA, gejala)
        intent.putExtra(EXTRA_POSITION, position)

        if(isEdit){
            kelolaDataViewModel.updateDataGejala(idGejala, kodeGejala, namaGejala).observe(this){
                if(it != null){
                    when(it){
                        is ResourceData.Loading ->{

                        }
                        is ResourceData.Success ->{
                            if(it.data?.error == false){
                                setResult(RESULT_UPDATE, intent)
                                finish()
                            }
                        }
                        is ResourceData.Error ->{

                        }
                    }
                }
            }
        }else{
            kelolaDataViewModel.insertDataGejala(kodeGejala, namaGejala).observe(this){
                if(it != null){
                    when(it){
                        is ResourceData.Loading ->{

                        }
                        is ResourceData.Success ->{
                            if(it.data?.error == false){
                                setResult(RESULT_ADD, intent)
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

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnSubmit ->{
                addUpdateData()
            }
        }
    }
}
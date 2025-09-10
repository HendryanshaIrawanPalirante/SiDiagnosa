package com.example.ta_hendryansha.view.admin.kelola_data

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityAddUpdatePenyakitBinding
import com.example.ta_hendryansha.entity.Penyakit
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData

class AddUpdatePenyakitActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddUpdatePenyakitBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }

    private var isEdit = false
    private var penyakit: Penyakit? = null
    private var position: Int = 0
    private var btnTitle: String? = null
    private var idPenyakit: String = ""
    companion object {
        const val EXTRA_PENYAKIT = "extra_penyakit"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdatePenyakitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        penyakit = intent.getParcelableExtra(EXTRA_PENYAKIT)
        idPenyakit = penyakit.toString()
        if(penyakit != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        }else{
            penyakit = Penyakit()
        }

        if(isEdit){
            btnTitle = "Edit"
            penyakit?.let {
                binding.inputNamaPenyakit.setText(it.namaPenyakit)
                binding.inputKodePenyakit.setText(it.kodePenyakit)
            }
        }else{
            btnTitle = "Simpan"
        }
        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener(this)

    }

    private fun addUpdateData(){
        val namaPenyakit = binding.inputNamaPenyakit.text.toString().trim()
        val kodePenyakit = binding.inputKodePenyakit.text.toString().trim()

        penyakit?.kodePenyakit = kodePenyakit
        penyakit?.namaPenyakit = namaPenyakit

        val intent = Intent()
        intent.putExtra(EXTRA_PENYAKIT, penyakit)
        intent.putExtra(EXTRA_POSITION, position)

        if(isEdit){
            kelolaDataViewModel.updateDataPenyakit(idPenyakit, kodePenyakit, namaPenyakit).observe(this){
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
            kelolaDataViewModel.insertDataPenyakit(kodePenyakit, namaPenyakit).observe(this){
                if(it != null){
                    when(it){
                        is ResourceData.Loading ->{

                        }
                        is ResourceData.Success ->{
                            if(it.data?.error == false){
                                setResult(AddUpdateGejalaActivity.RESULT_ADD, intent)
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
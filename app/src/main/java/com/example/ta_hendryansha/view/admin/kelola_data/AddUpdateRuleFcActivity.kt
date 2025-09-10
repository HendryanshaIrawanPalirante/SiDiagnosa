package com.example.ta_hendryansha.view.admin.kelola_data

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityAddUpdateRuleFcBinding
import com.example.ta_hendryansha.entity.RuleFc
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData

class AddUpdateRuleFcActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddUpdateRuleFcBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }

    private var isEdit = false
    private var ruleFc: RuleFc? = null
    private var position: Int = 0
    private var btnTitle: String? = null
    private var id: String = ""
    companion object {
        const val EXTRA_RULE_FC = "extra_penyakit"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateRuleFcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ruleFc = intent.getParcelableExtra(EXTRA_RULE_FC)
        id = ruleFc?.id.toString()
        if(ruleFc != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        }else{
            ruleFc = RuleFc()
        }

        if(isEdit){
            btnTitle = "Edit"
            ruleFc?.let {
                binding.inputIdKeputusan.setText(it.idGejala)
                binding.inputKeputusanYa.setText(it.ya)
                binding.inputKeputusanTidak.setText(it.tidak)
            }
        }else{
            btnTitle = "Simpan"
        }
        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun addUpdateDataRule(){
        val idGejala = binding.inputIdKeputusan.text.toString().trim()
        val ya = binding.inputKeputusanYa.text.toString().trim()
        val tidak = binding.inputKeputusanTidak.text.toString().trim()

        ruleFc?.idGejala = idGejala
        ruleFc?.ya = ya
        ruleFc?.tidak = tidak

        val intent = Intent()
        intent.putExtra(EXTRA_RULE_FC, ruleFc)
        intent.putExtra(EXTRA_POSITION, position)

        if(isEdit){
            kelolaDataViewModel.updateDataRuleFc(id, idGejala, ya, tidak).observe(this){
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
            kelolaDataViewModel.insertDataRuleFc(idGejala, ya, tidak).observe(this){
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

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnSubmit ->{
                addUpdateDataRule()
            }
        }
    }
}
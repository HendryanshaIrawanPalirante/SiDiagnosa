package com.example.ta_hendryansha.view.admin.kelola_data

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityAddUpdateRuleCfBinding
import com.example.ta_hendryansha.entity.RuleCf
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData

class AddUpdateRuleCfActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddUpdateRuleCfBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }

    private var isEdit = false
    private var ruleCf: RuleCf? = null
    private var position: Int = 0
    private var btnTitle: String? = null
    private var id: Int = 0
    private lateinit var sIntensitas: String
    private val strIntensitas = arrayOf("Selalu Dirasakan", "Terkadang Muncul/Hilang")
    private lateinit var sWaktu: String
    private val strWaktu = arrayOf("1-2 Hari", "Beberapa Hari", "1 Minggu", "2 Minggu")


    companion object {
        const val EXTRA_RULE_CF = "extra_rule"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateRuleCfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ruleCf = intent.getParcelableExtra(EXTRA_RULE_CF)
        id = ruleCf?.id ?: 0

        if(ruleCf != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        }else{
            ruleCf = RuleCf()
        }
        setSpinnerIntensitas()
        setSpinnerWaktu()

        if(isEdit){
            btnTitle = "Edit"
            ruleCf?.let {
                binding.inputKodeGejala.setText(it.kodeGejala)
                binding.inputKodePenyakit.setText(it.kodePenyakit)
                binding.inputNilaiCf.setText(it.nilaiCf.toString())
                val intensitas = strIntensitas.indexOf(it.intensitas)
                if (intensitas != -1) {
                    binding.spIntensitas.setSelection(intensitas)
                }
                val waktu = strWaktu.indexOf(it.waktu)
                if (waktu != -1) {
                    binding.spWaktu.setSelection(waktu)
                }
            }
        }else{
            btnTitle = "Simpan"
        }
        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun addUpdateDataRule(){
        val kodePenyakit = binding.inputKodePenyakit.text.toString().trim()
        val kodeGejala = binding.inputKodeGejala.text.toString().trim()
        val nilaiCf = binding.inputNilaiCf.text.toString().trim()
        val intensitas = sIntensitas
        val waktu = sWaktu

        ruleCf?.kodePenyakit = kodePenyakit
        ruleCf?.kodeGejala = kodeGejala
        ruleCf?.nilaiCf = nilaiCf.toDouble()

        val intent = Intent()
        intent.putExtra(EXTRA_RULE_CF, ruleCf)
        intent.putExtra(EXTRA_POSITION, position)

        if(isEdit){
            kelolaDataViewModel.updateDataRuleCf(id, kodePenyakit, kodeGejala, nilaiCf, intensitas, waktu).observe(this){
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
            kelolaDataViewModel.insertDataRuleCf(kodePenyakit,kodeGejala, nilaiCf, intensitas, waktu).observe(this){
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

    fun setSpinnerIntensitas(){
        val adapterGender = ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, strIntensitas)
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spIntensitas.adapter = adapterGender

        binding.spIntensitas.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    sIntensitas = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    fun setSpinnerWaktu(){
        val adapterGender = ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, strWaktu)
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spWaktu.adapter = adapterGender

        binding.spWaktu.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    sWaktu = parent.getItemAtPosition(position).toString()
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
                addUpdateDataRule()
            }
        }
    }
}
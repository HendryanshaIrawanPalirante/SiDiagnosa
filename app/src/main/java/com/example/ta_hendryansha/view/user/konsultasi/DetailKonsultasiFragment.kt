package com.example.ta_hendryansha.view.user.konsultasi

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.DialogFragment
import com.example.ta_hendryansha.R
import com.google.android.material.textfield.TextInputEditText

class DetailKonsultasiFragment : DialogFragment() {

    private lateinit var deskripsi: TextInputEditText
    private lateinit var spWaktu: AppCompatSpinner
    private lateinit var spIntensitas: AppCompatSpinner
    private lateinit var btnBenar: Button
    private lateinit var btnBatalkan: Button
    private val strWaktu = arrayOf("1-2 Hari", "Beberapa Hari", "1 Minggu", "2 Minggu")
    private var sWaktu: String? = null
    private val strIntensitas = arrayOf("Selalu Dirasakan", "Terkadang Muncul/Hilang")
    private var sIntensitas: String? = null
    private var detailKonsultasi: DetailKonsultasi? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deskripsi = view.findViewById<TextInputEditText>(R.id.deskripsi)
        spWaktu = view.findViewById<AppCompatSpinner>(R.id.spWaktu)
        spIntensitas = view.findViewById<AppCompatSpinner>(R.id.spIntensitas)
        btnBenar = view.findViewById<Button>(R.id.btn_benar)
        btnBatalkan = view.findViewById<Button>(R.id.btn_batalkan)
        setSpinnerAdapterWaktu()
        setSpinnerAdapterIntensitas()

        btnBenar.setOnClickListener{
            val deskripsi = deskripsi.text.toString().trim()
            val waktu = sWaktu
            val intensitas = sIntensitas
            detailKonsultasi?.dataKonsultasi(deskripsi, waktu, intensitas)
            dialog?.dismiss()
        }
        btnBatalkan.setOnClickListener {
            dialog?.cancel()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_konsultasi, container, false)
    }

    fun setSpinnerAdapterWaktu(){
        val adapterGender = ArrayAdapter<CharSequence>(requireActivity(), android.R.layout.simple_spinner_item, strWaktu)
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spWaktu.adapter = adapterGender

        spWaktu.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
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

    fun setSpinnerAdapterIntensitas(){
        val adapterGender = ArrayAdapter<CharSequence>(requireActivity(), android.R.layout.simple_spinner_item, strIntensitas)
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spIntensitas.adapter = adapterGender

        spIntensitas.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment
        if(fragment is KonsultasiFragment){
            this.detailKonsultasi = fragment.detailKonsultasi
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.detailKonsultasi = null
    }

    interface DetailKonsultasi{
        fun dataKonsultasi(deskripsi: String?, waktu: String?, intensitas: String?)
    }

}
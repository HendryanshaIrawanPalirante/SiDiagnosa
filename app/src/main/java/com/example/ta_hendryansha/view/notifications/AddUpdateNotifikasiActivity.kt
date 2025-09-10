package com.example.ta_hendryansha.view.notifications

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.DialogFragment
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.databinding.ActivityAddUpdateNotifikasiBinding
import com.example.ta_hendryansha.entity.Notifikasi
import com.example.ta_hendryansha.entity.User
import com.example.ta_hendryansha.model.UserViewModel
import com.example.ta_hendryansha.model.biodataModel.BiodataAdminModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.factoryModel.ViewModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.preference.UserPreference
import com.example.ta_hendryansha.remote.response.konsultasi.DetailKonsultasiItem
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.admin.kelola_data.AddUpdateRuleCfActivity
import com.example.ta_hendryansha.view.admin.kelola_user.AddUpdateUserActivity
import com.example.ta_hendryansha.view.user.konsultasi.DetailKonsultasiFragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddUpdateNotifikasiActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddUpdateNotifikasiBinding
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }
    private val biodataAdminModel: BiodataAdminModel by viewModels {
        UserModelFactory()
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val userViewModel: UserViewModel by viewModels {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }

    companion object {
        const val EXTRA_NOTIFIKASI = "extra_notifikasi"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isKonsultasi = false
    private var notifikasi: Notifikasi? = null
    private var position: Int = 0
    private var btnTitle: String? = null
    private var idUser: Int = 0
    private var nikDokter: Int = 0
    private var namaDokter: String = ""
    private var listDetailGejala = ArrayList<DetailKonsultasiItem>()
    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateNotifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notifikasi = intent.getParcelableExtra(EXTRA_NOTIFIKASI)
        idUser = notifikasi?.id ?: 0

        if(notifikasi != null){
            position = intent.getIntExtra(AddUpdateUserActivity.EXTRA_POSITION, 0)
            isKonsultasi = true
        }else{
            notifikasi = Notifikasi()
        }

        notifikasi?.let {
            id = it.id.toString()
            binding.tvNoBpjs.text = it.noBpjs.toString()
            binding.tvNama.text = it.namaPasien
            binding.tvNoKartuBerobat.text = it.noKartuBerobat
            binding.tvUmur.text = it.umur.toString()
            binding.tvJenisKelamin.text = it.jenisKelamin
            binding.tvHasilDiagnosa.text = it.hasilDiagnosa
            binding.tvWaktu.text = it.waktu
        }

        binding.btnDetail.setOnClickListener(this)

        userViewModel.getUser().observe(this){
            biodataAdminModel.getAdmin(it.email).observe(this){admin ->
                if(admin != null){
                    when(admin){
                        is ResourceData.Loading ->{

                        }
                        is ResourceData.Success ->{
                            val data = admin.data
                            if(data?.error == false){
                                nikDokter = data.data.nik
                                namaDokter = data.data.namaLengkap
                                binding.btnSubmit.setOnClickListener(this)
                            }
                        }
                        is ResourceData.Error ->{

                        }
                    }
                }
            }
        }
    }

    private fun kelolaNotifikasi(){
        val nikDokter = nikDokter
        val namaDokter = namaDokter
        val pengobatan = binding.edtPengobatan.text.toString().trim()
        val statusKonsultasi = "Selesai"
        val waktu = getCurrentDate()
        notifikasi?.let {
            kelolaDataViewModel.kelolaNotifikasi(it.id, nikDokter, namaDokter, it.noBpjs, it.noKartuBerobat, it.namaPasien, it.hasilDiagnosa, it.umur, it.jenisKelamin, pengobatan, statusKonsultasi, waktu).observe(this){kelolaNotif ->
                if(kelolaNotif != null){
                    when(kelolaNotif){
                        is ResourceData.Loading ->{

                        }
                        is ResourceData.Success ->{
                            setResult(RESULT_DELETE, intent)
                            finish()
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
                kelolaNotifikasi()
            }
            R.id.btn_detail ->{
                val openDetailGejalaFragment = DetailGejalaFragment()
                val fragmentManager = supportFragmentManager
                openDetailGejalaFragment.setData(id)
                openDetailGejalaFragment.show(fragmentManager, DetailGejalaFragment::class.java.simpleName)
            }
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

}
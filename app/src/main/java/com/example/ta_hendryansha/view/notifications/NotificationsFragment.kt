package com.example.ta_hendryansha.view.notifications

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ta_hendryansha.adapter.kelola_data.NotifikasiAdapter
import com.example.ta_hendryansha.databinding.FragmentNotificationsBinding
import com.example.ta_hendryansha.entity.Notifikasi
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.admin.kelola_user.AddUpdateUserActivity
import com.google.android.material.snackbar.Snackbar

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private val kelolaDataViewModel: KelolaDataViewModel by viewModels {
        UserModelFactory()
    }
    private lateinit var adapter: NotifikasiAdapter
    private val listNotifikasi = ArrayList<Notifikasi>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.data != null){
            when(result.resultCode){
                AddUpdateNotifikasiActivity.RESULT_DELETE -> {
                    val position =
                        result?.data?.getIntExtra(AddUpdateNotifikasiActivity.EXTRA_POSITION, 0) as Int
                    adapter.removeItem(position)
                    Snackbar.make(binding.rvNotifikasi, "Berhasil Melakukan Konsultasi", Snackbar.LENGTH_SHORT).show()
                }
            }

        }
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvNotifikasi.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotifikasi.setHasFixedSize(true)

        adapter = NotifikasiAdapter(object : NotifikasiAdapter.OnItemClickCallback{
            override fun onItemClicked(selectedNotifikasi: Notifikasi?, position: Int?) {
                val intent = Intent(requireContext(), AddUpdateNotifikasiActivity::class.java)
                intent.putExtra(AddUpdateNotifikasiActivity.EXTRA_NOTIFIKASI, selectedNotifikasi)
                intent.putExtra(AddUpdateUserActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })

        binding.rvNotifikasi.adapter = adapter

        if (savedInstanceState == null) {
            loadDataNotifikasi()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Notifikasi>(EXTRA_STATE)
            if (list != null) {
                adapter.listNotifikasi = list
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadDataNotifikasi(){
        kelolaDataViewModel.getNotifikasi().observe(viewLifecycleOwner){
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{

                    }
                    is ResourceData.Success ->{
                        val data = it.data
                        if(data?.error == true){

                        }else{
                            if(data != null){
                                for(dataResult in data.data){
                                    val notifikasi = Notifikasi(
                                        dataResult.id,
                                        dataResult.noBpjs,
                                        dataResult.noKartuBerobat,
                                        dataResult.namaPasien,
                                        dataResult.hasilDiagnosa,
                                        dataResult.umur,
                                        dataResult.jenisKelamin,
                                        dataResult.statusKonultasi,
                                        dataResult.waktu
                                    )
                                    listNotifikasi.add(notifikasi)
                                }
                            }
                            adapter.listNotifikasi = listNotifikasi
                            adapter.notifyDataSetChanged()
                        }
                    }
                    is ResourceData.Error ->{

                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotifikasi)
    }

}
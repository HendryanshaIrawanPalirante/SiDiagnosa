package com.example.ta_hendryansha.view.admin.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider

import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.adapter.menu.MenuAdapter
import com.example.ta_hendryansha.data.menu.DataMenuAdmin
import com.example.ta_hendryansha.model.MenuModel
import com.example.ta_hendryansha.model.UserViewModel
import com.example.ta_hendryansha.model.biodataModel.BiodataAdminModel
import com.example.ta_hendryansha.model.factoryModel.UserModelFactory
import com.example.ta_hendryansha.model.factoryModel.ViewModelFactory
import com.example.ta_hendryansha.preference.UserPreference
import com.example.ta_hendryansha.utils.ResourceData
import com.example.ta_hendryansha.view.welcome.WelcomeActivity

class HomeFragment : Fragment(), View.OnClickListener {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val biodataAdminModel: BiodataAdminModel by viewModels {
        UserModelFactory()
    }
    private lateinit var tvName: TextView
    private lateinit var tvJenisKelamin: TextView
    private lateinit var tvUmur: TextView
    private lateinit var tvNik: TextView
    private lateinit var tvStatus: TextView
    private lateinit var rvMenu: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private var listMenu = ArrayList<MenuModel>()
    private lateinit var nameBar: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val includeAppBar: View = view.findViewById(R.id.appBarLayout)
        nameBar = includeAppBar.findViewById(R.id.user_login)
        val logOut: View = includeAppBar.findViewById(R.id.btn_log_out)
        logOut.setOnClickListener(this)
        tvName = view.findViewById(R.id.tvNama)
        tvJenisKelamin = view.findViewById(R.id.tvJenisKelamin)
        tvUmur = view.findViewById(R.id.tvUmur)
        tvNik = view.findViewById(R.id.tvNik)
        tvStatus = view.findViewById(R.id.tvStatus)

        setInitLayout(view)
        setMenu()
        setData()

    }

    private fun setData(){
        val pref = requireContext().datastore
        val userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(pref))
        )[UserViewModel::class.java]

        userViewModel.getUser().observe(viewLifecycleOwner){
            biodataAdminModel.getAdmin(it.email).observe(viewLifecycleOwner){dataAdmin ->
                if(dataAdmin != null){
                    when(dataAdmin){
                        is ResourceData.Loading ->{

                        }
                        is ResourceData.Success ->{
                            val data = dataAdmin.data
                            if(data?.error == true){

                            }else{
                                data?.let {dataResult ->
                                    tvName.text = dataResult.data.namaLengkap
                                    tvJenisKelamin.text = dataResult.data.jenisKelamin
                                    tvUmur.text = dataResult.data.umur.toString()
                                    tvNik.text = dataResult.data.nik.toString()
                                    tvStatus.text = dataResult.data.pekerjaan
                                    nameBar.text = dataResult.data.namaLengkap
                                }
                            }

                        }
                        is ResourceData.Error ->{

                        }
                    }
                }
            }
        }
    }

    fun setInitLayout(view: View){
        rvMenu = view.findViewById(R.id.rvMenu)
        rvMenu.layoutManager = LinearLayoutManager(requireContext())
        rvMenu.setHasFixedSize(true)
    }

    fun setMenu(){
        if(listMenu.isEmpty()){
            listMenu.addAll(DataMenuAdmin.dataMenu())
        }
        menuAdapter = MenuAdapter(listMenu)
        rvMenu.adapter = menuAdapter
    }

    private fun setLogOut(){
        val pref = requireContext().datastore
        val userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(pref))
        )[UserViewModel::class.java]
        userViewModel.logout()
        startActivity(Intent(requireContext(), WelcomeActivity::class.java))
        requireActivity().finishAffinity()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_log_out ->{
                setLogOut()
            }
        }
    }
}
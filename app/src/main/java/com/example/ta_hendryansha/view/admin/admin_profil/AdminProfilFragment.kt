package com.example.ta_hendryansha.view.admin.admin_profil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.adapter.menu.MenuAdapter
import com.example.ta_hendryansha.data.menu.DataMenuKelolaUser
import com.example.ta_hendryansha.model.MenuModel

class AdminProfilFragment : Fragment() {

    private lateinit var rvMenu: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private var listMenu = ArrayList<MenuModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitLayout(view)
        setMenu()

    }

    fun setInitLayout(view: View){
        rvMenu = view.findViewById(R.id.rvMenu)
        rvMenu.layoutManager = LinearLayoutManager(requireContext())
        rvMenu.setHasFixedSize(true)
    }

    fun setMenu(){
        listMenu.addAll(DataMenuKelolaUser.dataMenu())
        menuAdapter = MenuAdapter(listMenu)
        rvMenu.adapter = menuAdapter
    }
}
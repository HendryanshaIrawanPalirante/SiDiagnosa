package com.example.ta_hendryansha.data.menu

import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.model.MenuModel

object DataMenuKelolaData {

    val arrayTitle = arrayOf(
        "Data Gejala",
        "Data Penyakit",
        "Data Rule Cf",
        "Data Rule Fc"
    )

    val arrayImage = arrayOf(
        R.drawable.kelola_data,
        R.drawable.kelola_data,
        R.drawable.kelola_data,
        R.drawable.kelola_data
    )

    fun dataMenu(): ArrayList<MenuModel>{
        val listMenu = ArrayList<MenuModel>()
        for (i in arrayTitle.indices){
            val menu = MenuModel(arrayTitle[i], arrayImage[i])
            listMenu.add(menu)
        }
        return listMenu
    }

}
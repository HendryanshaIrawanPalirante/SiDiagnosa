package com.example.ta_hendryansha.data.menu

import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.model.MenuModel

object DataMenuAdmin {

    val arrayTitle = arrayOf(
        "Riwayat"
    )

    val arrayImage = arrayOf(
        R.drawable.list_riwayat
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
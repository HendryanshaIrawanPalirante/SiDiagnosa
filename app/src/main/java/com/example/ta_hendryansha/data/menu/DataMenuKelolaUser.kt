package com.example.ta_hendryansha.data.menu

import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.model.MenuModel

object DataMenuKelolaUser {

    val arrayTitle = arrayOf(
        "Data Akun Admin",
        "Data Akun User"
    )

    val arrayImage = arrayOf(
        R.drawable.akun_admin,
        R.drawable.akun_user
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
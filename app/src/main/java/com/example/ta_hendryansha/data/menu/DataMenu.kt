package com.example.ta_hendryansha.data.menu

import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.model.MenuModel

object DataMenu {

    val arrayTitle = arrayOf(
        "Diagnosis",
        "Konsultasi Dokter",
        "Riwayat Penyakit",
        "Biodata"
    )

    val arrayImage = arrayOf(
        R.drawable.konsultasi,
        R.drawable.konsultasi_dokter,
        R.drawable.list_riwayat,
        R.drawable.biodata
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
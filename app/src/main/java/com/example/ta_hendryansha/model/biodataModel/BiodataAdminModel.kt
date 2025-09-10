package com.example.ta_hendryansha.model.biodataModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class BiodataAdminModel(private val repository: UserRepository): ViewModel() {

    fun getAdmin(email: String) = repository.getAdmin(email)

}
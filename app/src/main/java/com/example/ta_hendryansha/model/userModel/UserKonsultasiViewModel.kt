package com.example.ta_hendryansha.model.userModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class UserKonsultasiViewModel(private val repository: UserRepository): ViewModel() {

    fun getUser(noBpjs: Int) = repository.userKonsultasi(noBpjs)

}
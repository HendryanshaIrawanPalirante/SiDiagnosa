package com.example.ta_hendryansha.model.loginModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)

}
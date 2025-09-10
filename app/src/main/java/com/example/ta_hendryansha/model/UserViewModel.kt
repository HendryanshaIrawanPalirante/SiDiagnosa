package com.example.ta_hendryansha.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ta_hendryansha.model.userModel.UserModel
import com.example.ta_hendryansha.preference.UserPreference
import kotlinx.coroutines.launch

class UserViewModel(private val pref: UserPreference): ViewModel() {

    fun saveUser(user: UserModel){
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout(){
        viewModelScope.launch {
            pref.logout()
        }
    }

}
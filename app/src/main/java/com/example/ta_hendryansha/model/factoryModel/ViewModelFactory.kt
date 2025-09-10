package com.example.ta_hendryansha.model.factoryModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ta_hendryansha.model.UserViewModel
import com.example.ta_hendryansha.preference.UserPreference

class ViewModelFactory(private val pref: UserPreference):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(UserViewModel::class.java)->{
                UserViewModel(pref) as T
            }
            else ->throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

}
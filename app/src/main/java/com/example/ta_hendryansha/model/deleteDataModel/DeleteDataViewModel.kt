package com.example.ta_hendryansha.model.deleteDataModel

import androidx.lifecycle.ViewModel
import com.example.ta_hendryansha.data.repository.UserRepository

class DeleteDataViewModel(private val repository: UserRepository): ViewModel() {

    fun deleteData(namaTabel: String, id: String, action: String) = repository.deleteData(namaTabel, id, action)

}
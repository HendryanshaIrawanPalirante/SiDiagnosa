package com.example.ta_hendryansha.data

import android.content.Context
import com.example.ta_hendryansha.data.repository.UserRepository
import com.example.ta_hendryansha.remote.apiService.ApiConfig

object Injection {

    fun provideRepository(): UserRepository{
        val apiService = ApiConfig.getApiService()
        return UserRepository(apiService)
    }

}
package com.example.ta_hendryansha.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.ta_hendryansha.model.userModel.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preference ->
            UserModel(
                preference[EMAIL] ?: "",
                preference[TIPE_AKUN] ?: "",
                preference[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: UserModel){
        dataStore.edit { preference ->
            preference[EMAIL] = user.email
            preference[TIPE_AKUN] = user.tipeAkun
            preference[STATE_KEY] = user.isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val EMAIL = stringPreferencesKey("email")
        private val TIPE_AKUN = stringPreferencesKey("tipeAkun")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}
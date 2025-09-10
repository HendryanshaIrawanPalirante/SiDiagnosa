package com.example.ta_hendryansha.model.factoryModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ta_hendryansha.data.Injection
import com.example.ta_hendryansha.model.biodataModel.BiodataAdminModel
import com.example.ta_hendryansha.model.biodataModel.BiodataUserViewModel
import com.example.ta_hendryansha.model.daftarTungguKonsultasiModel.DaftarTungguKonsultasiViewModel
import com.example.ta_hendryansha.model.deleteDataModel.DeleteDataViewModel
import com.example.ta_hendryansha.model.kelolaDataModel.KelolaDataViewModel
import com.example.ta_hendryansha.model.konsultasiModel.HasilKonsultasiViewModel
import com.example.ta_hendryansha.model.konsultasiModel.KonsultasiDokterViewModel
import com.example.ta_hendryansha.model.konsultasiModel.KonsultasiViewModel
import com.example.ta_hendryansha.model.loginModel.LoginViewModel
import com.example.ta_hendryansha.model.notifikasiModel.NotifikasiViewModel
import com.example.ta_hendryansha.model.registerModel.RegisterViewModel
import com.example.ta_hendryansha.model.riwayatHasilKonsultasi.RiwayatKonsultasiViewModel
import com.example.ta_hendryansha.model.riwayatPenyakitModel.RiwayatPenyakitViewModel
import com.example.ta_hendryansha.model.userModel.UserKonsultasiViewModel

class UserModelFactory: ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(KonsultasiViewModel::class.java)){
            return KonsultasiViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(HasilKonsultasiViewModel::class.java)){
            return HasilKonsultasiViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(KonsultasiDokterViewModel::class.java)){
            return KonsultasiDokterViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(UserKonsultasiViewModel::class.java)){
            return UserKonsultasiViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(DaftarTungguKonsultasiViewModel::class.java)){
            return DaftarTungguKonsultasiViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(BiodataUserViewModel::class.java)){
            return BiodataUserViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(KelolaDataViewModel::class.java)){
            return KelolaDataViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(BiodataAdminModel::class.java)){
            return BiodataAdminModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(DeleteDataViewModel::class.java)){
            return DeleteDataViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(RiwayatKonsultasiViewModel::class.java)){
            return RiwayatKonsultasiViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(RiwayatPenyakitViewModel::class.java)){
            return RiwayatPenyakitViewModel(Injection.provideRepository()) as T
        }else if(modelClass.isAssignableFrom(NotifikasiViewModel::class.java)){
            return NotifikasiViewModel(Injection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
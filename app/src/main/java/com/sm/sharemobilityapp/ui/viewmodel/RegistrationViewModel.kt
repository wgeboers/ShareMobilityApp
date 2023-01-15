package com.sm.sharemobilityapp.ui.viewmodel

import androidx.lifecycle.*
import com.sm.sharemobilityapp.network.ShareMobilityApi
import com.sm.sharemobilityapp.network.UserInfo
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val _apiResponse = MutableLiveData<String>()

    val apiResponse: LiveData<String>
        get() = _apiResponse


    fun registerUser(userInfo: UserInfo) {
        viewModelScope.launch {
            ShareMobilityApi.retrofitService.postUser(userInfo)
        }
    }
}

class RegistrationViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegistrationViewModel() as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}
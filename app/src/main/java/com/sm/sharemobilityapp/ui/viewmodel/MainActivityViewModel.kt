package com.sm.sharemobilityapp.ui.viewmodel

import androidx.lifecycle.*
import com.sm.sharemobilityapp.network.ShareMobilityApi
import kotlinx.coroutines.launch

class MainActivityViewModel() : ViewModel() {

    private val _loginSuccessful = MutableLiveData<Boolean>()
    val loginSuccessful: LiveData<Boolean>
        get() = _loginSuccessful

    private val _apiResponse = MutableLiveData<String>()

    val apiResponse: LiveData<String>
        get() = _apiResponse


    fun login(username: String, password: String) {
        viewModelScope.launch {
            val response = ShareMobilityApi.retrofitService.loginWithResponse(username, password)
            if(response.isSuccessful) {
                _loginSuccessful.value = true
            }
            _apiResponse.value = "${response.code()}"
        }
    }
}

class MainActivityViewModelFactory() : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel() as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }

}
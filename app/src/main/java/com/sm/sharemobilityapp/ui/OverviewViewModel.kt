package com.sm.sharemobilityapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.sharemobilityapp.network.ShareMobilityApi
import com.sm.sharemobilityapp.network.UserInfo
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

//    init {
//        getUsers()
//    }


    fun getUsers() {
        viewModelScope.launch {
            try {
                val listResult = ShareMobilityApi.retrofitService.getUsers()
                _status.value = listResult.toString()
               // _status.value = "Succes: ${listResult.size} users retrieved.\n"
               // _status.value += listResult.joinToString(separator = "\r\n")
                _status.value = listResult.toString()
            } catch (e: java.lang.Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    fun getUser(id: Long) {
        viewModelScope.launch {
            try {
                val result = ShareMobilityApi.retrofitService.getUser(id)
                _status.value = result.toString()
            } catch (e: java.lang.Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    fun getLogin(username: String, password: String) {
        viewModelScope.launch {
            try {
                _status.value = ShareMobilityApi.retrofitService.getLogin(username, password).toString()
            } catch (e: java.lang.Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    fun getCars() {
        viewModelScope.launch {
            try {
                val listCars = ShareMobilityApi.retrofitService.getCars()
                _status.value = listCars.toString()
            } catch (e: java.lang.Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    fun postUserInfo(userInfo: UserInfo) {
        viewModelScope.launch {
            try {
               _status.value = ShareMobilityApi.retrofitService.postUser(userInfo).toString()
            } catch (e: java.lang.Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}
package com.sm.sharemobilityapp.overview

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
}
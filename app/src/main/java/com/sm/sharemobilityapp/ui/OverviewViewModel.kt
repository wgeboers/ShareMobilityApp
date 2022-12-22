package com.sm.sharemobilityapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.sharemobilityapp.network.ShareMobilityApi
import com.sm.sharemobilityapp.network.UserInfo
import kotlinx.coroutines.launch
import com.sm.sharemobilityapp.data.User

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

   // lateinit var loginUser: UserInfo

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo>
        get() = _userInfo

//    init {
//        getUsers()
//    }


    fun getUsers() {
        viewModelScope.launch {
            try {
                val listResult = ShareMobilityApi.retrofitService.getUsers()
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
                _userInfo.value = ShareMobilityApi.retrofitService.getLogin(username, password)
            } catch (e: java.lang.Exception) {
                //TO DO: what kind of error/message
               // loginUser = "Failure: ${e.message}"
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
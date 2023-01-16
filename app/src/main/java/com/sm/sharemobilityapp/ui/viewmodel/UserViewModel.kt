package com.sm.sharemobilityapp.ui.viewmodel

import androidx.lifecycle.*
import com.sm.sharemobilityapp.network.ShareMobilityApi
import com.sm.sharemobilityapp.network.UserInfo
import kotlinx.coroutines.launch

class UserViewModel() : ViewModel() {

    private val _apiResponse = MutableLiveData<String?>()

    val apiResponse: LiveData<String?>
        get() = _apiResponse


    private val _userInfo = MutableLiveData<UserInfo>()

    val userInfo: LiveData<UserInfo>
        get() = _userInfo

    fun getUser(id: Int) {
        viewModelScope.launch {
            val result = ShareMobilityApi.retrofitService.getUserWithResponse(id)
            if(result.isSuccessful) {
                _userInfo.value = result.body()
                _apiResponse.value = null
            } else {
                _apiResponse.value = result.code().toString()
            }
        }
    }

    fun updateUser(userInfo: UserInfo) {
        viewModelScope.launch {
            ShareMobilityApi.retrofitService.putUser(userInfo, _userInfo.value!!.id!!)
            _apiResponse.value = "updated item ${_userInfo.value!!.id!!}"
        }
    }

    fun setUser(user: UserInfo) {
        _userInfo.value = user
    }
}

class UserViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel() as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }

}
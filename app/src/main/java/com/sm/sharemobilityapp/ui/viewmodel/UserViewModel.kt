package com.sm.sharemobilityapp.ui.viewmodel

import androidx.lifecycle.*
import com.sm.sharemobilityapp.network.ShareMobilityApi
import com.sm.sharemobilityapp.network.UserInfo
import kotlinx.coroutines.launch

class UserViewModel() : ViewModel() {

    private val _apiResponse = MutableLiveData<String>()

    val apiResponse: LiveData<String>
        get() = _apiResponse


    private val _userInfo = MutableLiveData<UserInfo>()

    val userInfo: LiveData<UserInfo>
        get() = _userInfo

    fun getUser(id: Int) {
        viewModelScope.launch {
            val result = ShareMobilityApi.retrofitService.getUser(id)
            _userInfo.value = result
        }
    }

    fun deleteUserWithResponse(id: Int) {
        viewModelScope.launch {
            val getAllResponse = ShareMobilityApi.retrofitService.getUserWithResponse(id)
            if (getAllResponse.isSuccessful) {
                val deleteResponse = ShareMobilityApi.retrofitService.deleteUserWithResponse(id)
                if (deleteResponse.isSuccessful) {
                    _apiResponse.value = "User with id: ${getAllResponse.body()?.id} deleted"
                } else {
                    _apiResponse.value =
                        "Item id: ${getAllResponse.body()?.id} deletion unsuccessful" +
                                "Response code: ${deleteResponse.code()}"
                }
            } else {
                _apiResponse.value =
                    "No items to be deleted Response code: ${getAllResponse.code()} message: ${getAllResponse.message()}"
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
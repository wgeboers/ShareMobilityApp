package com.sm.sharemobilityapp.ui.viewmodel

import androidx.lifecycle.*
import com.sm.sharemobilityapp.BaseApplication
import com.sm.sharemobilityapp.data.Car
import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.data.User
import com.sm.sharemobilityapp.data.UserDao
import com.sm.sharemobilityapp.network.UserInfo
import com.sm.sharemobilityapp.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class UserViewModel(private val userDao: UserDao): ViewModel() {
    
    val dataRepository = DataRepository(SMRoomDatabase.getDatabase(BaseApplication()))
    private val _userlist = MutableLiveData<List<User>>()
    val userlist: LiveData<List<User>>
        get() = _userlist

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

//    fun insertCar(car: Car) {
//        viewModelScope.launch(Dispatchers.IO) {
//            carDao.insert(car)
//        }
//    }

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                dataRepository.refreshUsers()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(userlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    private fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insert(user)
        }
    }

    private fun getNewUserEntry(userInfo: UserInfo) : User {
        return User(
             id = userInfo.id!!,
             type = userInfo.type.toString(),
             username = userInfo.username.toString(),
             password = userInfo.password.toString(),
             firstname = userInfo.firstname.toString(),
             lastname = userInfo.lastname.toString(),
             address = userInfo.address.toString(),
        )
    }

    fun addNewUser(userInfo: UserInfo) {
        val newUser = getNewUserEntry(userInfo)
        insertUser(newUser)
    }
}

class UserViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            //@Suppress("UNCHECKED_CAST")
            return UserViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
       // return super.create(modelClass)
    }
}
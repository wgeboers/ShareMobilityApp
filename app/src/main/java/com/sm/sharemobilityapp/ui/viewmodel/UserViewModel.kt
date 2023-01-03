package com.sm.sharemobilityapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sm.sharemobilityapp.data.User
import com.sm.sharemobilityapp.data.UserDao
import com.sm.sharemobilityapp.network.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao): ViewModel() {

    private fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            //userDao.insert(user)
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
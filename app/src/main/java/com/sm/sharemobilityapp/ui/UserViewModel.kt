package com.sm.sharemobilityapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sm.sharemobilityapp.data.User
import com.sm.sharemobilityapp.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao): ViewModel() {

    private fun insertUser(userType: String, username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userInfo = User(
                type = userType,
                username = username,
                password = password,
                firstname = "Piet",
                lastname = "Klaas",
                address = "Straat",
            )
            userDao.insert(userInfo)
        }
    }

    private fun getNewUserEntry(userType: String, username: String, password: String) : User {
        return User(
             type = userType,
             username = username,
             password = password,
             firstname = "Piet",
             lastname = "Klaas",
             address = "Straat",
        )
    }

    fun addNewUser(userType: String, username: String, password: String) {
        //val newUser = getNewUserEntry(userType, username, password)
        insertUser(userType, username, password)
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
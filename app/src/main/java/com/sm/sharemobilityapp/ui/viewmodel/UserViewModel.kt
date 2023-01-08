package com.sm.sharemobilityapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.sm.sharemobilityapp.BaseApplication
import com.sm.sharemobilityapp.data.Car
import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.data.User
import com.sm.sharemobilityapp.data.UserDao
import com.sm.sharemobilityapp.network.UserInfo
import com.sm.sharemobilityapp.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException

class UserViewModel(private val userDao: UserDao): ViewModel() {
    
    val dataRepository = DataRepository(SMRoomDatabase.getDatabase(BaseApplication()))
    private val _userlist = MutableLiveData<List<User>>()
    val userlist: LiveData<List<User>>
        get() = _userlist

    private val _carlist = MutableLiveData<List<Car>>()
    val carlist: LiveData<List<Car>>
        get() = dataRepository.cars.asLiveData()

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                dataRepository.getAllCars()
            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                Log.d("UserViewModel", networkError.message.toString())
            }
        }
    }

    fun printModels() {
        viewModelScope.launch {
            dataRepository.getCarsByFilter("Hyundai", "ix0", 0.0, 9000.0).collect() {
                value -> Log.d("Cars filtered", value.toString())
            }

        }
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
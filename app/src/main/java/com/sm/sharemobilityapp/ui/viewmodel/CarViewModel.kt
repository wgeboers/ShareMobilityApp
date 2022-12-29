package com.sm.sharemobilityapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sm.sharemobilityapp.data.Car
import com.sm.sharemobilityapp.data.CarDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel(private val carDao: CarDao): ViewModel() {

    fun insertCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            carDao.insert(car)
        }
    }
}

class CarViewModelFactory(private val carDao: CarDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
            //@Suppress("UNCHECKED_CAST")
            return CarViewModel(carDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
        // return super.create(modelClass)
    }
}
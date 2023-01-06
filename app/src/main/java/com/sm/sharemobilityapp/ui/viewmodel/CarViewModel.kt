package com.sm.sharemobilityapp.ui.viewmodel

import androidx.lifecycle.*
import com.sm.sharemobilityapp.data.Car
import com.sm.sharemobilityapp.data.CarDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CarViewModel(private val carDao: CarDao): ViewModel() {

    fun insertCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            carDao.insert(car)
        }
    }

    var carsFlow : Flow<List<Car>> = carDao.getCars()

    var carsLiveData : LiveData<List<Car>> = carDao.getCars().asLiveData()
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
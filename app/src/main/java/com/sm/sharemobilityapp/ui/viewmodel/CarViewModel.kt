package com.sm.sharemobilityapp.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.sm.sharemobilityapp.BaseApplication
import androidx.lifecycle.*
import com.sm.sharemobilityapp.data.Car
import com.sm.sharemobilityapp.data.CarDao
import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.network.CarInfo
import com.sm.sharemobilityapp.network.Registration
import com.sm.sharemobilityapp.network.ReservationInfo
import com.sm.sharemobilityapp.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class CarViewModel(private val carDao: CarDao): ViewModel() {
    private val dataRepository = DataRepository(SMRoomDatabase.getDatabase(BaseApplication()))
    private var _carInfo: MutableLiveData<CarInfo> = MutableLiveData<CarInfo>()

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkMessage = MutableLiveData<String>()
    val isNetworkMessage: LiveData<String>
        get() = _isNetworkMessage

    private var _isNetworkMessageRegistration = MutableLiveData<String>()
    val isNetworkMessageRegistration: LiveData<String>
        get() = _isNetworkMessageRegistration

    val carinfo: LiveData<CarInfo>
        get() = _carInfo

    fun insertCar(carInfo: CarInfo) {
        Log.d("CarVM Carinfo", carInfo.toString())
        viewModelScope.launch() {
            try {
                val response = dataRepository.insertCar(carInfo)
                if (response.code() == 201) {
                    _carInfo.postValue(response.body())
                }
                _isNetworkMessage.value = response.code().toString()
            } catch (networkError: IOException) {
                    _isNetworkMessage.value = networkError.message
            }
        }
    }

    fun insertRegistration(carId: Int, ownerId: Int) {
        viewModelScope.launch {
            try {
                val response = dataRepository.postRegistration(carId, ownerId)
                _isNetworkMessageRegistration.value = response.code().toString()
            } catch (networkError: IOException) {
                _isNetworkMessageRegistration.value = networkError.message
            }
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
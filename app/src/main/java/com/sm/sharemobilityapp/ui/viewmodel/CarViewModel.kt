package com.sm.sharemobilityapp.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.sm.sharemobilityapp.BaseApplication
import com.sm.sharemobilityapp.data.Car
import com.sm.sharemobilityapp.data.CarDao
import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.network.CarInfo
import com.sm.sharemobilityapp.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class CarViewModel(private val carDao: CarDao): ViewModel() {
    private val dataRepository = DataRepository(SMRoomDatabase.getDatabase(BaseApplication()))
    private var _carInfo: MutableLiveData<CarInfo> = MutableLiveData<CarInfo>()

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val carinfo: LiveData<CarInfo>
        get() = _carInfo

    fun insertCar(carInfo: CarInfo) {
        viewModelScope.launch() {
            try {
                val response = dataRepository.insertCar(carInfo)
                if (response.code() == 201) {
                    _carInfo.postValue(response.body())
                }
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            }catch (networkError: IOException) {
                _eventNetworkError.value = true
            }
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
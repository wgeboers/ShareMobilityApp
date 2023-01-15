package com.sm.sharemobilityapp.ui.viewmodel

import androidx.lifecycle.*
import com.sm.sharemobilityapp.network.RegistrationInfo
import com.sm.sharemobilityapp.network.ShareMobilityApi
import kotlinx.coroutines.launch

class CarOwnerListViewModel : ViewModel() {

    private val _apiResponse = MutableLiveData<String>()

    val apiResponse: LiveData<String>
        get() = _apiResponse

    private val _carData = MutableLiveData<List<RegistrationInfo>>()

    val carData: LiveData<List<RegistrationInfo>>
        get() = _carData

    fun getCarsByOwner(id: Int) {
        viewModelScope.launch {
            var result = ShareMobilityApi.retrofitService.getAllRegistrationsById(id)
            _carData.value = result
        }
    }

    fun getCarsByOwnerTest() {
        viewModelScope.launch {
            var result = ShareMobilityApi.retrofitService.getAllRegistrationsById(1)
            _carData.value = result
        }
    }
}

class CarOwnerListViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarOwnerListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarOwnerListViewModel() as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}
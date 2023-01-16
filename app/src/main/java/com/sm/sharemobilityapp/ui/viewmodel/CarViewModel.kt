package com.sm.sharemobilityapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.sm.sharemobilityapp.data.car.Car
import com.sm.sharemobilityapp.data.getDatabase
import com.sm.sharemobilityapp.model.CarModel
import com.sm.sharemobilityapp.network.CarInfo
import com.sm.sharemobilityapp.repository.CarRepository
import com.sm.sharemobilityapp.repository.ReservationRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

class CarViewModel(application: Application) : AndroidViewModel(application) {
    //Filter
    private val _cityFilter = MutableLiveData<String?>()
    val cityFilter: LiveData<String?> = _cityFilter

    private val _radiusFilter = MutableLiveData<String?>()
    val radiusFilter: LiveData<String?> = _radiusFilter

    private val _brandFilter = MutableLiveData<String?>()
    val brandFilter: LiveData<String?> = _brandFilter

    private val _modelFilter = MutableLiveData<String?>()
    val modelFilter: LiveData<String?> = _modelFilter

    private val _priceFromFilter = MutableLiveData<Double?>()
    val priceFromFilter: LiveData<Double?> = _priceFromFilter

    private val _priceTillFilter = MutableLiveData<Double?>()
    val priceTillFilter: LiveData<Double?> = _priceTillFilter

    private val _idFilter = MutableLiveData<Int?>()
    val idFilter: LiveData<Int?> = _idFilter

    private val _carPricePerHour = MutableLiveData<Double?>()
    val carPricePerHour: LiveData<Double?> = _carPricePerHour

    fun setIdFilter(id: Int?) {
        _idFilter.value = id
    }

    fun setCarPricePerHour(price: Double?) {
        _carPricePerHour.value = price
    }

    fun setCityFilter(city: String?) {
        _cityFilter.value = city
    }

    fun clearCityFilter() {
        _cityFilter.value = null
    }

    fun setRadiusFilter(radius: String?) {
        _radiusFilter.value = radius
    }

    fun clearRadiusFilter() {
        _radiusFilter.value = null
    }

    fun setBrandFilter(brand: String?) {
        _brandFilter.value = brand
    }

    fun clearBrandFilter() {
        _brandFilter.value = null
    }

    fun setModelFilter(model: String?) {
        _modelFilter.value = model
    }

    fun clearModelFilter() {
        _modelFilter.value = null
    }

    fun setPriceFromFilter(priceFrom: Double?) {
        _priceFromFilter.value = priceFrom
    }

    fun clearPriceFromFilter() {
        _priceFromFilter.value = null
    }

    fun setPriceTillFilter(priceTill: Double?) {
        _priceTillFilter.value = priceTill
    }

    fun clearPriceTillFilter() {
        _priceTillFilter.value = null
    }

    fun areFiltersSet(): Boolean {
        var state: Boolean = false
        if (cityFilter.value != null) {
            state = true
        } else if (radiusFilter.value != null) {
            state = true
        } else if (brandFilter.value != null) {
            state = true
        } else if (modelFilter.value != null) {
            state = true
        } else if (priceFromFilter.value != null) {
            state = true
        } else if (priceTillFilter.value != null) {
            state = true
        }
        return state
    }

    //Einde filter

    //Car data
    // The data source this ViewModel will fetch results from.
    private val carRepository = CarRepository(getDatabase(application))
    private var _carInfo: MutableLiveData<CarInfo> = MutableLiveData<CarInfo>()

    var cars = carRepository.cars

    var car = carRepository.cars.map {
        it.filter {
            it.id == idFilter.value
        }
    }

    var filteredCars = carRepository.cars.map {
        it.filter {
            it.make == brandFilter.value &&
            it.model == modelFilter.value
            it.hourlyRate!! >= priceFromFilter.value!! &&
            it.hourlyRate <= priceTillFilter.value!!
        }
    }

    var brands = carRepository.brands
    var models = carRepository.models

    var carsByUser: Flow<List<CarModel>> = emptyFlow()

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    private var _isNetworkMessageRegistration = MutableLiveData<String>()
    val isNetworkMessageRegistration: LiveData<String> get() = _isNetworkMessageRegistration

    private var _isNetworkMessage = MutableLiveData<String>()
    val isNetworkMessage: LiveData<String> get() = _isNetworkMessage

    val carinfo: LiveData<CarInfo>
        get() = _carInfo

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                carRepository.refreshCars()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                if (cars.count() == 0)
                    _eventNetworkError.value = true
            }
        }
    }

    fun insertRegistration(carId: Int, ownerId: Int) {
        viewModelScope.launch {
            try {
                val response = carRepository.postRegistration(carId, ownerId)
                _isNetworkMessageRegistration.value = response.code().toString()
            } catch (networkError: IOException) {
                _isNetworkMessageRegistration.value = networkError.message
            }
        }
    }

    fun insertCar(carInfo: CarInfo) {
        Log.d("CarVM Carinfo", carInfo.toString())
        viewModelScope.launch() {
            try {
                val response = carRepository.insertCar(carInfo)
                if (response.code() == 201) {
                    _carInfo.postValue(response.body())
                }
                _isNetworkMessage.value = response.code().toString()
            } catch (networkError: IOException) {
                _isNetworkMessage.value = networkError.message
            }
        }
    }

    fun getCarsByUser(userId: Int){
        viewModelScope.launch {
            carsByUser = carRepository.getCarsByUser(userId)
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class CarViewModelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CarViewModel(app) as T
            }
            throw java.lang.IllegalArgumentException("Unable to construct viewmodel")
        }
    }
    //Eide car data
}
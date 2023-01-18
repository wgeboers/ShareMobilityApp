package com.sm.sharemobilityapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sm.sharemobilityapp.data.getDatabase
import com.sm.sharemobilityapp.data.reservation.Reservation
import com.sm.sharemobilityapp.model.ReservationModel
import com.sm.sharemobilityapp.network.ShareMobilityApi
import com.sm.sharemobilityapp.network.UserInfo
import com.sm.sharemobilityapp.repository.CarRepository
import com.sm.sharemobilityapp.repository.ReservationRepository
import com.sm.sharemobilityapp.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.Duration
import java.time.LocalDate
import java.time.Period

class ReservationViewModel(application: Application) : AndroidViewModel(application) {
    private val reservationRepository = ReservationRepository(getDatabase(application))
    private val carRepository = CarRepository(getDatabase(application))
    private val userRepository = UserRepository(getDatabase(application))
    var reservations = reservationRepository.reservations

    var reservationsByUser: Flow<List<ReservationModel>> = emptyFlow()

    var cars = carRepository.cars
    var users = userRepository.users

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    private val _startRentDate = MutableLiveData<String?>()
    val startRentDate: LiveData<String?> = _startRentDate

    private val _endRentDate = MutableLiveData<String?>()
    val endRentDate: LiveData<String?> = _endRentDate

    private val _totalPrice = MutableLiveData<Double?>()
    val totalPrice: LiveData<Double?> = _totalPrice

    fun setStartRentDate(startDate: String?) {
        _startRentDate.value = startDate
    }

    fun clearStartRentDate() {
        _startRentDate.value = null
    }

    fun setEndRentDate(endDate: String?) {
        _endRentDate.value = endDate
    }

    fun clearEndRentDate() {
        _endRentDate.value = null
    }

    fun setTotalPrice(pph: Double) {
        val startDate = LocalDate.parse(startRentDate.value)
        val endDate = LocalDate.parse(endRentDate.value)
        val days = Period.between(startDate,endDate).days+1
        val hours = days*24

        _totalPrice.value = hours*pph
    }

    fun clearTotalPrice() {
        _totalPrice.value = null
    }

    init {
        refreshDataFromRepository()
    }

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                reservationRepository.refreshReservations()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                if (reservations.count() == 0)
                    _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun areDatesSet(): Boolean {
        var state: Boolean = false
        state = startRentDate.value != null && endRentDate.value != null

        return state
    }

    fun postReservation(userId: Int, carId: Int, startDate: String, endDate: String) {
        viewModelScope.launch {
            val reservation = Reservation(null, carId, userId, startDate, endDate)
            reservationRepository.insertReservation(reservation)
        }
    }

    fun getReservationsByUser(userId: Int){
        viewModelScope.launch {
            reservationsByUser = reservationRepository.getReservationsByUser(userId)
        }
    }

    class ReservationViewModelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CarViewModel(app) as T
            }
            throw java.lang.IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
package com.sm.sharemobilityapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sm.sharemobilityapp.data.getDatabase
import com.sm.sharemobilityapp.data.reservation.Reservation
import com.sm.sharemobilityapp.network.CarInfo
import com.sm.sharemobilityapp.network.ReservationInfo
import com.sm.sharemobilityapp.repository.CarRepository
import com.sm.sharemobilityapp.repository.ReservationRepository
import com.sm.sharemobilityapp.repository.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class ReservationViewModel(application: Application) : AndroidViewModel(application) {
    private val _startRentDate = MutableLiveData<String?>()
    val startRentDate: LiveData<String?> = _startRentDate

    private val _endRentDate = MutableLiveData<String?>()
    val endRentDate: LiveData<String?> = _endRentDate

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

    private val reservationRepository = ReservationRepository(getDatabase(application))
    private val carRepository = CarRepository(getDatabase(application))
    private val userRepository = UserRepository(getDatabase(application))
    var reservations = reservationRepository.reservations
    var cars = carRepository.cars
    var users = userRepository.users

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
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

    fun areDatesSet(): Boolean{
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
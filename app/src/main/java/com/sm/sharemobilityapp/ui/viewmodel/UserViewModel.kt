package com.sm.sharemobilityapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sm.sharemobilityapp.data.getDatabase
import com.sm.sharemobilityapp.repository.UserRepository
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import java.io.IOException

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(getDatabase(application))
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
                userRepository.refreshUsers()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                if (users.count() == 0)
                    _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class UserViewModelFactory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CarViewModel(app) as T
            }
            throw java.lang.IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
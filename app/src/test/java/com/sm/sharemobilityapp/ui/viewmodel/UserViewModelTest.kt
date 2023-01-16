package com.sm.sharemobilityapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sm.sharemobilityapp.network.UserInfo
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class UserViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val userObject = UserInfo(1, "CAR_OWNER", "bf", "123456", "Bart", "Frijters", "Pelsakker 7", 10)

    @Test
    fun test_userInfo() {
        val viewModel = UserViewModel()
        viewModel.userInfo.observeForever{ }
        viewModel.setUser(userObject)
        assertEquals("User object is the same", userObject, viewModel.userInfo.value)
    }
}

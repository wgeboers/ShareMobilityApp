package com.sm.sharemobilityapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MainActivityViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test_userId() {
        val viewModel = MainActivityViewModel()
        viewModel.userId.observeForever {}
        viewModel.setUserId(1)
        assertEquals("id correct",1, viewModel.userId.value)
    }

    @Test
    fun test_loggedIn() {
        val viewModel = MainActivityViewModel()
        viewModel.loginSuccessful.observeForever {  }
        viewModel.setLoggedIn(true)
        assertEquals("logged in correct", true, viewModel.loginSuccessful.value)
    }
}
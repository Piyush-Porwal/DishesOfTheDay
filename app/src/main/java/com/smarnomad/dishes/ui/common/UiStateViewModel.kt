package com.smarnomad.dishes.ui.common

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smarnomad.dishes.utils.Constants
import com.smarnomad.dishes.utils.Event
import com.smarnomad.dishes.utils.UiStateManager

/**
 * Interface to be implemented by ViewModel class if it makes network requests or updates UI
 */
open class UiStateViewModel : ViewModel() {

    var error = MutableLiveData(Event(0))

    /**
     * helper to return show remote error with error code
     */
    val showErrorView = ObservableInt(-1)


    /**
     * [UiStateManager]
     */
    private var _uiState = UiStateManager.UiState.LOADED

    var uiState: UiStateManager.UiState
        get() {
            return _uiState
        }
        set(value) {
            when (value) {
                UiStateManager.UiState.INIT -> {
                    isLoading.set(true)
                }
                UiStateManager.UiState.LOADING -> {
                    isLoading.set(true)
                }

                UiStateManager.UiState.INIT_EMPTY -> {
                    error.value = Event(404)
                    hasNoData.set(true)
                    hasError.set(true)
                    isLoading.set(false)
                }
                UiStateManager.UiState.EMPTY -> {
                    error.value = Event(404)
                    hasNoData.set(true)
                    hasError.set(true)
                    isLoading.set(false)
                }
                UiStateManager.UiState.ERROR_CONNECTION -> {
                    error.value = Event(Constants.RESPONSE_1001_CONNECTION_FAILURE)
                    hasNetworkError.set(true)
                    hasError.set(true)
                    isLoading.set(false)
                }

                UiStateManager.UiState.INVALID_CREDENTIALS -> {
                    error.value = Event(406)
                    hasError.set(true)
                    isLoading.set(false)
                }

                UiStateManager.UiState.HTTP_ERROR_FORBIDDEN -> {
                    error.value = Event(403)
                    hasError.set(true)
                    isLoading.set(false)
                }

                UiStateManager.UiState.ERROR -> {
                    error.value = Event(500)
                    hasError.set(true)
                    isLoading.set(false)
                }

                else -> {
                    error.value = Event(0)
                    isLoading.set(false)
                    hasNoData.set(false)
                    hasNetworkError.set(false)
                    hasError.set(false)
                }
            }
            _uiState = value
        }

    /**
     * helper to return all cases when the view is in loading state
     */
    val isLoading = ObservableBoolean(false)

    /**
     * helper to return all cases when view is not having any data
     */
    val hasNoData = ObservableBoolean(false)

    /**
     * helper to return all cases when view could not be loaded due to network error
     */
    val hasNetworkError = ObservableBoolean(false)

    /**
     * helper to return all cases when view could not be loaded due to unknown error
     */
    val hasError = ObservableBoolean(false)
}


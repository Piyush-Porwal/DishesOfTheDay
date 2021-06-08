package com.smarnomad.dishes.ui.dishdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smarnomad.dishes.data.dishes.DishesRepository
import com.smarnomad.dishes.data.dishes.entity.Dishes
import com.smarnomad.dishes.ui.common.UiStateViewModel
import com.smarnomad.dishes.utils.Constants
import com.smarnomad.dishes.utils.Event
import com.smarnomad.dishes.utils.UiStateManager
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class DishDetailsViewModel @Inject constructor(private val dishesRepository: DishesRepository) :
    UiStateViewModel() {

    val dishes = MutableLiveData<Event<com.smarnomad.dishes.data.dishes.model.Dishes>>()

    fun loadDishes(id: Int) {
        uiState = UiStateManager.UiState.LOADING
        viewModelScope.launch {
            val (response, statusCode) = try {
                dishesRepository.getDishFromRemote(id)
            } catch (e: IOException) {
                uiState = UiStateManager.UiState.ERROR_CONNECTION
                showErrorView.set(Constants.RESPONSE_1001_CONNECTION_FAILURE)
                null to 0
            } catch (e: Exception) {
                uiState = UiStateManager.UiState.ERROR
                showErrorView.set(Constants.RESPONSE_500_SERVER_ERROR)
                null to 0
            }

            if (statusCode == Constants.RESPONSE_200_SUCCESS) response?.let {
                uiState = if (it != null) {
                    showErrorView.set(-1)
                    dishes.value = Event(it)
                    UiStateManager.UiState.LOADED
                } else {
                    showErrorView.set(Constants.RESPONSE_404_NOT_FOUND)
                    UiStateManager.UiState.EMPTY
                }
            } else if (statusCode != 0) {
                UiStateManager.UiState.ERROR
                showErrorView.set(Constants.RESPONSE_500_SERVER_ERROR)
            }
        }
    }
}
package com.smarnomad.dishes.ui.dishesoftheday

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

class DishesOfTheDayViewModel @Inject constructor(private val dishesRepository: DishesRepository) :
    UiStateViewModel() {

    var dishes = dishesRepository.getDishesLiveData()

    private var dishesEntity: Dishes? = null

    fun loadDishesData() {
        uiState = UiStateManager.UiState.INIT
        viewModelScope.launch {
            dishesEntity = dishesRepository.getDishesData()

            if (dishesEntity != null) {
                uiState = UiStateManager.UiState.LOADED
            }
            syncDishes()
        }
    }

    private fun syncDishes() {
        uiState = UiStateManager.UiState.LOADING
        viewModelScope.launch {
            val (response, statusCode) = try {
                dishesRepository.getDishesOfTheDayFromRemote()
            } catch (e: IOException) {
                handleError(Constants.RESPONSE_1001_CONNECTION_FAILURE)
                null to 0
            } catch (e: Exception) {
                handleError(500)
                null to 0
            }

            if (statusCode == Constants.RESPONSE_200_SUCCESS) response?.let {
                uiState = if (it != null) {
                    showErrorView.set(-1)
                    dishesRepository.saveDishesData(it)
                    UiStateManager.UiState.LOADED
                } else {
                    dishesRepository.deleteDishesFromDB()
                    handleError(404)
                    UiStateManager.UiState.EMPTY
                }
            } else if (statusCode != 0) {
                handleError(statusCode)
            }
        }
    }

    private fun handleError(errorCode: Int) {
        if (dishesEntity == null) {
            uiState = UiStateManager.UiState.ERROR
            showErrorView.set(errorCode)
        } else {
            uiState = UiStateManager.UiState.ERROR
            error.value = Event(errorCode)
            showErrorView.set(-1)
        }
    }
}
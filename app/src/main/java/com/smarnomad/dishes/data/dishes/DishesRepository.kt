package com.smarnomad.dishes.data.dishes

import androidx.lifecycle.LiveData
import com.smarnomad.dishes.data.dishes.model.Dishes
import com.smarnomad.dishes.utils.async.ThreadManager
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DishesRepository @Inject constructor(
    private val api: DishesApi,
    private val threadManager: ThreadManager,
    private val dishesDataSourceLocal: DishesDataSourceLocal
) {

    /**
     * Save dishes data  in db
     *
     * @param dishes [Dishes] List of dishes
     */
    suspend fun saveDishesData(dishes: Dishes) {
        val dishesEntity = com.smarnomad.dishes.data.dishes.entity.Dishes(
            id = dishes.id,
            name = dishes.name,
            imageUrl = dishes.imageUrl,
            shortDescription = dishes.shortDescription,
            wikiLink = dishes.wikiLink,
            shareLink = dishes.shareLink,
            moreImages = dishes.moreImages
          )

        dishesDataSourceLocal.insetDishes(dishesEntity)
    }

    /**
     * Get dishes live data
     */
    fun getDishesLiveData(): LiveData<com.smarnomad.dishes.data.dishes.entity.Dishes> =
        dishesDataSourceLocal.getDishesLiveDta()

    /**
     * Get dishes data
     */
    suspend fun getDishesData(): com.smarnomad.dishes.data.dishes.entity.Dishes =
        dishesDataSourceLocal.getDishes()

    /**
     * Delete dishes data
     */
    suspend fun deleteDishesFromDB() = dishesDataSourceLocal.deleteAll()


    /**
     * Get dishesOfTheDay from remote
     */
    suspend fun getDishesOfTheDayFromRemote(): Pair<Dishes?, Int> {
        return withContext(threadManager.io) {
            val response = api.getDishesOfTheDay()
            response.body() to response.code()
        }
    }

    /**
     * Get dish from remote
     */
    suspend fun getDishFromRemote(id: Int): Pair<Dishes?, Int> {
        val param = HashMap<String,String>()
        param["id"] = id.toString()

        return withContext(threadManager.io) {
            val response = api.getDish()
            response.body() to response.code()
        }
    }

}

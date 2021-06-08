package com.smarnomad.dishes.data.dishes

import androidx.lifecycle.LiveData
import com.smarnomad.dishes.data.dishes.dao.DishesDao
import com.smarnomad.dishes.data.dishes.entity.Dishes
import javax.inject.Inject

open class DishesDataSourceLocal @Inject constructor( private val dishesDao: DishesDao) {

    /**
     * Get dishes LiveData
     *
     */
    open fun getDishesLiveDta(): LiveData<Dishes> {
        return dishesDao.getDishLiveData()
    }

    /**
     * Insert dishes Data
     *
     */
    open suspend fun insetDishes(dishes: Dishes) {
        return dishesDao.insert(dishes)
    }

    /**
     * Get dishes Data
     *
     */
    open suspend fun getDishes(): Dishes {
        return dishesDao.getDish()
    }

    /**
     * Delete all data from local db
     *
     */
    open suspend fun deleteAll() {
        dishesDao.nuke()
    }
}
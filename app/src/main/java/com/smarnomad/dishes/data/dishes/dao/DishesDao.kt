package com.smarnomad.dishes.data.dishes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.smarnomad.dishes.data.dishes.entity.Dishes

@Dao
interface DishesDao {

    @Query("Select COUNT(id) From dishes")
    suspend fun count(): Int

    @Query("Select * From dishes")
    suspend fun getDish(): Dishes

    @Query("Select * From dishes")
    fun getDishLiveData(): LiveData<Dishes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dishes: Dishes)

    @Update
    suspend fun update(dishes: Dishes)

    @Query("Delete From dishes")
    suspend fun nuke()
}
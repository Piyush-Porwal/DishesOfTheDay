package com.smarnomad.dishes.data.dishes

import com.smarnomad.dishes.data.dishes.model.Dishes
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap


/**
 * Service to make API calls for Dishes
 */
interface DishesApi {

    /**
     * Get List of all dishes of the day
     */
    @GET("/dishesoftheday")
    @Throws(Exception::class)
    suspend fun getDishesOfTheDay(): Response<Dishes>

    /**
     * Get List of all dishes of the day
     */
    @GET("/dishes/234")
    @Throws(Exception::class)
    suspend fun getDish(): Response<Dishes> //get request query parameter not accepting 404


    companion object {
        /**
         * Factory function for [DishesApi]
         */
        fun create(retroFit: Retrofit): DishesApi = retroFit.create(
            DishesApi::class.java
        )
    }
}
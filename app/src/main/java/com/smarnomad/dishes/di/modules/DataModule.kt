package com.smarnomad.dishes.di.modules

import android.app.Application
import com.smarnomad.dishes.data.DishesDatabase
import com.smarnomad.dishes.data.dishes.dao.DishesDao
import dagger.Module
import dagger.Provides

/**
 * Data module
 */
@Module
class DataModule {

    @Module
    companion object {

        /**
         * [DishesDao]
         */
        @JvmStatic
        @Provides
        fun provideUserDao(application: Application): DishesDao =
            DishesDatabase.getInstance(application).dishesDao()
    }
}

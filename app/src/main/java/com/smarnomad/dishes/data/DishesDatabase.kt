package com.smarnomad.dishes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smarnomad.dishes.BuildConfig
import com.smarnomad.dishes.data.dishes.dao.DishesDao
import com.smarnomad.dishes.data.dishes.entity.Dishes

/**
 * Database helper for News Headlines Database
 */
@Database(
    entities = [Dishes::class],
    version = BuildConfig.VERSION_CODE,
    exportSchema = true
)

@TypeConverters(TypeConverter::class)
abstract class DishesDatabase : RoomDatabase() {

    /**
     * See [DishesDao]
     */
    abstract fun dishesDao(): DishesDao

    companion object {

        // For Singleton instantiation
        @Volatile
        internal var instance: DishesDatabase? = null
            private set

        private const val DATABASE_NAME: String = "DishesDatabase"

        /**
         * Build and return an instance of [DishesDatabase]
         */
        fun getInstance(context: Context): DishesDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): DishesDatabase {
            return Room.databaseBuilder(context, DishesDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}

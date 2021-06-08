package com.smarnomad.dishes.di.modules

import android.app.Application
import com.smarnomad.dishes.DishesApp
import com.smarnomad.dishes.di.modules.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Module(
    includes = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        NetModule::class,
        DataModule::class
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): DishesApp = application as DishesApp
}

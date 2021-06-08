package com.smarnomad.dishes

import android.app.Application
import com.smarnomad.dishes.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class DishesApp: Application(), HasAndroidInjector {

    /**
     * Injector for Android components
     */
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    /**
     * See [Application.onCreate]
     */
    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().app(this)
            .build()
            .inject(this)
    }

    /**
     * @return injector for Android components
     */
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}
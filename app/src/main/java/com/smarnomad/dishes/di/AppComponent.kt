package com.smarnomad.dishes.di

import android.app.Application
import com.smarnomad.dishes.DishesApp
import com.smarnomad.dishes.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppBindings::class
    ]
)
interface AppComponent : AndroidInjector<DishesApp> {
    fun inject(app: Application)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(application: Application): Builder

        fun build(): AppComponent
    }
}

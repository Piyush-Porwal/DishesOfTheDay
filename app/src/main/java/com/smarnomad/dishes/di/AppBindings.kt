package com.smarnomad.dishes.di

import com.smarnomad.dishes.MainActivity
import com.smarnomad.dishes.utils.async.ThreadManager
import com.smarnomad.dishes.utils.async.ThreadManagerImpl
import com.smarnomad.dishes.di.bindings.FragmentBindings
import com.smarnomad.dishes.di.bindings.ViewModelBindings
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindings {

    @ContributesAndroidInjector(
        modules = [
            FragmentBindings::class,
            ViewModelBindings::class
        ]
    )

    abstract fun contributeMainActivity(): MainActivity

    @Binds
    abstract fun provideThreadManager(threadManager: ThreadManagerImpl): ThreadManager
}


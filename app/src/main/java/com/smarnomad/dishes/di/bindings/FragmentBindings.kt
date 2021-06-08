package com.smarnomad.dishes.di.bindings

import com.smarnomad.dishes.ui.dishdetails.DishDetailsFragment
import com.smarnomad.dishes.ui.dishesoftheday.DishesOfTheDayFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindings {

    @ContributesAndroidInjector
    abstract fun contributeDishesOfTheDayFragment(): DishesOfTheDayFragment

    @ContributesAndroidInjector
    abstract fun contributeDishDetailsFragment(): DishDetailsFragment
}

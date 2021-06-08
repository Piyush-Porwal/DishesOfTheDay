package com.smarnomad.dishes.di.bindings

import androidx.lifecycle.ViewModel
import com.smarnomad.dishes.MainViewModel
import com.smarnomad.dishes.di.modules.viewmodel.ViewModelKey
import com.smarnomad.dishes.ui.dishdetails.DishDetailsViewModel
import com.smarnomad.dishes.ui.dishesoftheday.DishesOfTheDayViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBindings {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DishesOfTheDayViewModel::class)
    abstract fun bindDishesOfTheDayViewModel(viewModel: DishesOfTheDayViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DishDetailsViewModel::class)
    abstract fun bindDishDetailsViewModel(viewModel: DishDetailsViewModel): ViewModel
}

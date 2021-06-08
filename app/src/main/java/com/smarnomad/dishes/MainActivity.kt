package com.smarnomad.dishes

import android.os.Bundle
import androidx.activity.viewModels
import com.smarnomad.dishes.databinding.ActivityMainBinding
import com.smarnomad.dishes.di.modules.viewmodel.ViewModelFactory
import com.smarnomad.dishes.utils.extensions.setDataBindingView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    /**
     * Custom factory for viewmodel
     *
     * Custom factory provides app related dependencies
     */
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    /**
     * onCreate()
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setDataBindingView(R.layout.activity_main)

        initView()
        initObservers()
    }

    private fun initView() {
    }

    private fun initObservers() {
    }
}

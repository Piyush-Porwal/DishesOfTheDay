package com.smarnomad.dishes.ui.dishesoftheday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smarnomad.dishes.R
import com.smarnomad.dishes.data.dishes.entity.Dishes
import com.smarnomad.dishes.databinding.FragmentDishesOfTheDayBinding
import com.smarnomad.dishes.di.modules.viewmodel.ViewModelFactory
import com.smarnomad.dishes.ui.imageslider.ImageSliderFragment
import com.smarnomad.dishes.utils.extensions.setDataBindingView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DishesOfTheDayFragment: DaggerFragment() {

    /**
     * Custom factory for viewmodel
     *
     * Custom factory provides app related dependencies
     */
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: DishesOfTheDayViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentDishesOfTheDayBinding

    private var dishes: Dishes? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = setDataBindingView(R.layout.fragment_dishes_of_the_day, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        loadDishOfTheDay()
    }

    private fun initView() {
        binding.viewmodel = viewModel
        binding.errorView.viewmodel = viewModel
        binding.errorView.refreshBtn.setOnClickListener {
            loadDishOfTheDay()
        }

        binding.dishOfTheDayCv.setOnClickListener {
            val directions = DishesOfTheDayFragmentDirections.actionNavigationDishesOfTheDayToNavigationDishDetails(
                dishes?.id!!)
            findNavController().navigate(directions)
        }

        binding.showMoreImagesTv.setOnClickListener{
            val imageSliderDialogFragment = dishes?.moreImages?.let { it1 -> ImageSliderFragment(it1) }
            imageSliderDialogFragment?.show(
                childFragmentManager,
                imageSliderDialogFragment.tag
            )
        }
    }

    private fun initObserver(){
        viewModel.dishes.observe(viewLifecycleOwner,{
            binding.dishes = it
            dishes = it
        })
    }

    private fun loadDishOfTheDay(){
        viewModel.loadDishesData()
    }

}
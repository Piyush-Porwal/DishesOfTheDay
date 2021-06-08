package com.smarnomad.dishes.ui.dishdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.smarnomad.dishes.R
import com.smarnomad.dishes.databinding.FragmentDishDetailsBinding
import com.smarnomad.dishes.di.modules.viewmodel.ViewModelFactory
import com.smarnomad.dishes.utils.EventObserver
import com.smarnomad.dishes.utils.extensions.setDataBindingView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DishDetailsFragment: DaggerFragment() {

    /**
     * Custom factory for viewmodel
     *
     * Custom factory provides app related dependencies
     */
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: DishDetailsViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentDishDetailsBinding
    private var dishId: Int = 0

    private var dishes: com.smarnomad.dishes.data.dishes.model.Dishes? = null

    private val args: DishDetailsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dishId = args.id
        loadDish(dishId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = setDataBindingView(R.layout.fragment_dish_details, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView(){
        binding.viewmodel = viewModel
        binding.errorView.viewmodel = viewModel
        binding.errorView.refreshBtn.setOnClickListener {
            loadDish(dishId)
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.readMore.setOnClickListener {
            val uris = Uri.parse(dishes?.wikiLink)
            val intents = Intent(Intent.ACTION_VIEW, uris)
            startActivity(intents)
        }

        binding.shareBtn.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, dishes?.shareLink)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun initObserver(){
        viewModel.dishes.observe(viewLifecycleOwner,EventObserver{
            dishes = it
            binding.dishes = it
        })
    }

    private fun loadDish(id: Int){
        viewModel.loadDishes(id)
    }
}
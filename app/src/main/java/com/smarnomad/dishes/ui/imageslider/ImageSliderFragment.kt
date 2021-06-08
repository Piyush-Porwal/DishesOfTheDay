package com.smarnomad.dishes.ui.imageslider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.smarnomad.dishes.R
import com.smarnomad.dishes.databinding.FragmetnImageSliderBinding
import com.smarnomad.dishes.utils.extensions.setDataBindingView
import kotlin.math.abs

class ImageSliderFragment(private val imageList: List<String>): DialogFragment() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: FragmetnImageSliderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = setDataBindingView(R.layout.fragmetn_image_slider, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        viewPager2 = binding.viewPagerImageSlider
        viewPager2.adapter = ImageSliderAdapter(imageList)

        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(30))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r*0.25f
        }

        viewPager2.setPageTransformer(compositePageTransformer)
    }
}
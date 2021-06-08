package com.smarnomad.dishes.ui.imageslider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smarnomad.dishes.databinding.ImageSlideContainerBinding

class ImageSliderAdapter(private val sliderItemList: List<String>): RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>(){

    class ImageSliderViewHolder(private val binding:ImageSlideContainerBinding): RecyclerView.ViewHolder(binding.root){
      fun bind(sliderItem: String){
          binding.apply {
              binding.imageUrl = sliderItem
          }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        return ImageSliderViewHolder(
            ImageSlideContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.bind(sliderItemList[position])
    }

    override fun getItemCount(): Int {
       return sliderItemList.size
    }
}
package com.smarnomad.dishes.utils.databinding

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.facebook.shimmer.ShimmerFrameLayout
import com.smarnomad.dishes.R
import com.smarnomad.dishes.utils.Constants

/**
 * Custom binding adapter for Views
 */
object ViewBindingAdapters {

    /**
     * Glide data binding adapter
     * Custom binding adapter to fetch and load image in an [android.widget.ImageView] using [Glide]
     *
     * @param imageView ImageView to load image
     * @param url Url of image to be loaded
     * @param placeholder Placeholder drawable while image is being fetched
     */
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
    fun setImageUsingGlide(
        imageView: ImageView, url: String?, placeholder: Drawable?
    ) {

        if (!url.isNullOrBlank()) {
            // Outline clipping is helpful in drawing rounded corners
            imageView.clipToOutline = true

            Glide.with(imageView)
                .load(url).placeholder(placeholder)
                .centerCrop()
                .transition(
                    DrawableTransitionOptions().crossFade()
                ).into(imageView)
        }
    }

    /**
     * Glide data binding adapter
     * Custom binding adapter to fetch and load image in an [View] using [Glide]
     *
     * @param view View to load image
     * @param url Url of image to be loaded
     * @param placeholder Placeholder drawable while image is being fetched
     */
    @JvmStatic
    @BindingAdapter(value = ["viewImageUrl", "viewPlaceHolder"], requireAll = false)
    fun setImageToViewUsingGlide(
        view: View, url: String?, placeholder: Drawable?
    ) {

        if (!url.isNullOrBlank()) {
            // Outline clipping is helpful in drawing rounded corners
            view.clipToOutline = true

            Glide.with(view)
                .load(url).placeholder(placeholder)
                .centerCrop()
                .transition(
                    DrawableTransitionOptions().crossFade()
                ).into(object : CustomTarget<Drawable>(){
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                      view.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
        }
    }

    @JvmStatic
    @BindingAdapter("isGone")
    fun bindIsGone(view: View, isGone: Boolean) {
        view.visibility = if (isGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun bindIsVisible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("isLoading")
    fun toggleShimmerEffect(shimmerFrameLayout: ShimmerFrameLayout, isLoading: Boolean) {
        if (!isLoading) {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
        } else {
            shimmerFrameLayout.startShimmer()
            shimmerFrameLayout.visibility = View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("context", "errorType")
    fun setErrorMsg(errorTv: TextView, context: Context, errorType: Int) {
        when (errorType) {
            Constants.RESPONSE_401_UNAUTHORISED -> errorTv.text = context.resources.getString(R.string.unauthorised_access)
            Constants.RESPONSE_404_NOT_FOUND -> errorTv.text = context.resources.getString(R.string.no_data_found)
            Constants.RESPONSE_500_SERVER_ERROR -> errorTv.text = context.resources.getString(R.string.something_went_wrong)
            Constants.RESPONSE_1001_CONNECTION_FAILURE -> errorTv.text = context.resources.getString(R.string.no_internet_connection)
            -1,0 -> {}
            else -> errorTv.text = context.resources.getString(R.string.something_went_wrong)
        }
    }

}

package com.smarnomad.dishes.network

import com.smarnomad.dishes.utils.extensions.isNetNotConnected
import com.smarnomad.dishes.DishesApp
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NoConnectionInterceptor @Inject constructor(
    private val context: DishesApp
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (context.isNetNotConnected()) {
            throw NoInternetException()
        }else {
            chain.proceed(chain.request())
        }
    }
}
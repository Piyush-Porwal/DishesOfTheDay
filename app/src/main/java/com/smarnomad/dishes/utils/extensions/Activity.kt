package com.smarnomad.dishes.utils.extensions

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.smarnomad.dishes.R
import com.smarnomad.dishes.utils.Constants

/**
 * File will contain all the extension functions for [Activity]s
 */

fun <T : ViewDataBinding> Activity.setDataBindingView(layoutId: Int): T =
    DataBindingUtil.setContentView(this, layoutId)

/**
 * Extension function to create and show a Snackbar
 *
 * Also customised the Snackbar ui for app
 *
 * @param text message for Snackbar
 * @param snackBarType Type of Snackbar [SnackbarType]
 */
fun AppCompatActivity.showSnackbar(
    text: String,
    snackBarType: SnackbarType = SnackbarType.Success
) {

    val contentView = findViewById<View>(android.R.id.content)
    val snackbar = Snackbar.make(contentView, text, Snackbar.LENGTH_LONG)

    // Customise the Snackbar using an extension function
//    snackbar.customise(text, snackBarType)
    snackbar.show()
}

/**
 * Extension function for showing Snackbar with type [SnackbarType.Warning]
 *
 *  @param text Warning message
 */
fun AppCompatActivity.showWarningSnackbar(text: String) =
    showSnackbar(text, SnackbarType.Warning)

/**
 * Extension function for showing Snackbar with type [SnackbarType.Error]
 *
 * @param text Error message
 */
fun AppCompatActivity.showErrorSnackbar(text: String) =
    showSnackbar(text, SnackbarType.Error)

/**
 * Extension function for showing Snackbar with type [SnackbarType.Success]
 *
 * @param text Success message
 */
fun AppCompatActivity.showSuccessSnackbar(text: String) =
    showSnackbar(text, SnackbarType.Success)

/**
 * Extension function for showing the Activity in fullscreen mode
 */
fun Activity.requestGestureUi() {
    val view = window.decorView
    if (hasQ()) {
        view.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        window.navigationBarColor = ContextCompat.getColor(this, R.color.transparent)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
    } else {
        view.systemUiVisibility = 0
    }
}


/**
 * Hide Navigation bar
 */
fun Activity.requestLowProfileUi(request: Boolean = true) {
    val view = window.decorView
    if (request) {
        view.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    } else {
        view.systemUiVisibility = 0
    }
}

/**
 * Check if PIP is supported on the current platform
 */
fun AppCompatActivity.hasPipSupport(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)

/**
 * Check if notification channels are supported on the current platform
 */
fun hasNotificationChannelSupport(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

/**
 * Check if gesture UI is supported on device
 */
fun hasQ(): Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

/**
 * Check if device has Nougat
 */
fun hasNougat(): Boolean =
    Build.VERSION.SDK_INT in listOf(Build.VERSION_CODES.N, Build.VERSION_CODES.N_MR1)

/**
 * Open a custom tab
 */
fun FragmentActivity.launchCustomTab(uri: Uri) {
    val builder = CustomTabsIntent.Builder()
    builder.setShowTitle(true)
    builder.setToolbarColor(ContextCompat.getColor(this, R.color.primary))
    builder.setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right)
    builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
    val drawable = AppCompatResources.getDrawable(
        this, R.drawable.ic_material_icon_arrow_back
    )
    drawable?.let {
        builder.setCloseButtonIcon(drawable.toBitmap())
    }
    val customTabsIntent = builder.build()
    customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    customTabsIntent.launchUrl(this, uri)
}


/**
 * Create a [AlertDialog]
 */
fun AppCompatActivity.createDialog(
    title: String,
    message: String,
    positiveButton: String,
    positiveButtonClickListener: (() -> Unit)? = null,
    negativeButton: String,
    negativeButtonClickListener: (() -> Unit)? = null,
    showNegative: Boolean,
    cancellable: Boolean = true
): AlertDialog {
    val builder = MaterialAlertDialogBuilder(this)
    builder.setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButton) { dialog, _ ->
            positiveButtonClickListener?.invoke()
            dialog.dismiss()
        }.setCancelable(cancellable)

    if(showNegative){
        builder.setNegativeButton(negativeButton) { dialog, _ ->
            negativeButtonClickListener?.invoke()
            dialog.dismiss()
        }
    }

    return builder.create()
}

fun AppCompatActivity.showSnackbarOnRemoteError(errorCode: Int){
    when(errorCode){
        Constants.RESPONSE_401_UNAUTHORISED -> showSnackbar("Unauthorised Access",
            SnackbarType.Error
        )
        Constants.RESPONSE_404_NOT_FOUND -> showSnackbar("No Data Found", SnackbarType.Error)
        Constants.RESPONSE_500_SERVER_ERROR -> showSnackbar("Something Went Wrong",
            SnackbarType.Error
        )
        Constants.RESPONSE_1001_CONNECTION_FAILURE -> showSnackbar("No Internet Connection.",
            SnackbarType.Error
        )
        -1, 0 -> {}
        else -> showSnackbar("Something Went Wrong.", SnackbarType.Error)
    }
}




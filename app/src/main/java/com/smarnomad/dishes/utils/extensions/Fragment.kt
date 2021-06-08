package com.smarnomad.dishes.utils.extensions

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * File will contain all the extension functions for [androidx.fragment.app.Fragment]s
 */

/**
 * Extension function to check connectivity
 *
 * @return Will return True if connected, otherwise False
 */
fun Fragment.isNetConnected(): Boolean = requireContext().isNetConnected()

/**
 * Start a custom tab for Uri
 *
 * @param uri Url to be opened
 */
fun Fragment.launchCustomTab(uri: Uri): Unit = requireActivity().launchCustomTab(uri)

/**
 * Extension function to check if no connectivity
 *
 * Inverse function to [Fragment.isNetConnected]
 *
 * @return Will return True if not connected, otherwise False
 */
fun Fragment.isNetNotConnected(): Boolean = !isNetConnected()

/**
 * Extension function to check connectivity status is metered
 *
 * @return Will return True if metered, otherwise False
 */
fun Fragment.isActiveNetworkMetered(): Boolean = requireContext().isActiveNetworkMetered()

/**
 * Extension function to inflate a databinding layout from a Fragment
 *
 * Call this function from [Fragment.onCreateView] to get [ViewDataBinding]
 *
 * @param layoutId Int Layout resource id
 * @param container parent layout
 *
 *
 * @return ViewDataBinding binding for layout resource
 */
fun <T : ViewDataBinding> Fragment.setDataBindingView(
    @LayoutRes
    layoutId: Int,
    container: ViewGroup?
): T =
    (DataBindingUtil.inflate(
        layoutInflater,
        layoutId,
        container,
        false
    ) as T).apply {

        // Setting lifecycle owner to viewLifecycleOwner for databinding with LiveData
        lifecycleOwner = viewLifecycleOwner
    }

/**
 * Extension function for showing Snackbar with type [SnackbarType.Warning]
 *
 *  @param text Warning message
 */
fun Fragment.showWarningSnackbar(text: String) {
    (requireActivity() as? AppCompatActivity)?.showSnackbar(text, SnackbarType.Warning)
}

/**
 * Extension function for showing Snackbar with type [SnackbarType.Error]
 *
 * @param text Error message
 */
fun Fragment.showErrorSnackbar(text: String) {
    (requireActivity() as? AppCompatActivity)?.showSnackbar(text, SnackbarType.Error)
}

/**
 * Extension function for showing Snackbar with type [SnackbarType.Success]
 *
 * @param text Success message
 */
fun Fragment.showSuccessSnackbar(text: String) {
    (requireActivity() as? AppCompatActivity)?.showSnackbar(text, SnackbarType.Success)
}

/**
 * Hide keyboard
 */
fun Fragment.hideKeyboard() {
    val inputMethodManager =
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    view?.let {
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
        requireView().clearFocus()
    }
}


fun Fragment.showUIAboveInputMethod(){
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}

/**
* Soft Input Mode
*
*/

fun Fragment.setSoftInputMode(){
    activity?.window?.setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    )
}

/**
 * Request landscape orientation
 */
fun Fragment.requestLandscapeOrientation(isLandscape: Boolean = true) {
    requireActivity().requestedOrientation = if (isLandscape) {
        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    } else {
        ActivityInfo.SCREEN_ORIENTATION_USER
    }
}

/**
 * Create a [AlertDialog]
 */
fun Fragment.createDialog(
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes positiveButton: Int,
    positiveButtonClickListener: (() -> Unit)? = null,
    @StringRes negativeButton: Int,
    negativeButtonClickListener: (() -> Unit)? = null,
    cancellable: Boolean = true
): AlertDialog? = if (isVisible) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(getString(title))
        .setMessage(getString(message))
        .setPositiveButton(getString(positiveButton)) { dialog, _ ->
            positiveButtonClickListener?.invoke()
            dialog.dismiss()
        }
        .setNegativeButton(getString(negativeButton)) { dialog, _ ->
            negativeButtonClickListener?.invoke()
            dialog.dismiss()
        }.setCancelable(cancellable).create()
} else {
    null
}


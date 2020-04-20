package co.temy.android.ktx

import android.content.res.Resources
import android.util.DisplayMetrics

/**
 * Converts value in pixels into value in device-independent pixels
 */
fun Int.toDp(): Int {
    val metrics = Resources.getSystem().displayMetrics
    return this / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * Converts value in device-independent pixels into value in pixels
 */
fun Int.toPx(): Int {
    val metrics = Resources.getSystem().displayMetrics
    return this * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}
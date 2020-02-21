package co.temy.android.ktx.context

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.AdaptiveIconDrawable
import android.net.Uri
import android.os.Build
import android.provider.Settings.*
import android.util.DisplayMetrics
import android.view.ContextThemeWrapper
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

fun Context.isTabletConfig(): Boolean = resources.configuration.smallestScreenWidthDp >= SMALLEST_WIDTH_600

fun Context.isPortraitSw600Config(): Boolean =
    resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT &&
            resources.configuration.smallestScreenWidthDp >= SMALLEST_WIDTH_600

fun Context.isLandscapeSw600Config(): Boolean =
    resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE &&
            resources.configuration.smallestScreenWidthDp >= SMALLEST_WIDTH_600

fun Context.isLandscapeDisplayOrientation(): Boolean = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun Activity.getDisplayWidth(): Int {
    val displayMetrics: DisplayMetrics = DisplayMetrics()

    this.windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun Activity.enablePortraitScreenOrientationForMobile(isPortrait: Boolean) {
    if (!isTabletConfig()) {
        if (isPortrait) requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }
}

fun Activity.lockScreenOrientationChanges(lock: Boolean) {
    requestedOrientation = if (lock) ActivityInfo.SCREEN_ORIENTATION_LOCKED else ActivityInfo.SCREEN_ORIENTATION_SENSOR
}

internal fun Context.addLifecycleObserver(observer: LifecycleObserver) {
    when {
        this is LifecycleOwner -> this.lifecycle.addObserver(observer)
        this is ContextThemeWrapper -> this.baseContext.addLifecycleObserver(observer)
        this is androidx.appcompat.view.ContextThemeWrapper -> this.baseContext.addLifecycleObserver(observer)
    }
}

fun Activity.openEmailClient(emailTo: String, subject: String) {
    val uriString = "mailto:$emailTo?subject=$subject"
    val emailIntent = Intent(Intent.ACTION_SENDTO)

    emailIntent.data = Uri.parse(uriString)

    if (emailIntent.resolveActivity(packageManager) != null) {
        startActivity(emailIntent)
    } else {
        startActivity(Intent.createChooser(emailIntent, null))
    }
}

fun Context.getMiddleScreenX(): Int {
    val screenEndX = this.resources.displayMetrics.widthPixels
    return (screenEndX / 2)
}

fun Context.getMiddleScreenY(): Int {
    val screenEndY = this.resources.displayMetrics.heightPixels
    return (screenEndY / 2)
}

fun Activity.openNotificationSettings() {
    val intent = Intent()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        intent.action = ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra(EXTRA_APP_PACKAGE, packageName)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        intent.action = ACTION_APP_NOTIFICATION_SETTINGS
        intent.putExtra("app_package", packageName)
        intent.putExtra("app_uid", applicationInfo.uid)
    } else {
        intent.action = ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:$packageName")
    }
    startActivity(intent)
}

/**
 * Starting from appcompat v.1.1.0 system overrides baseContext after [attachBaseContext]
 * causing resetting locale settings. To prevent this we must call [applyOverrideConfiguration]
 * with modified [overrideConfiguration] object.
 *
 * That's a workaround until this behaviour will be fixed in future appcompat releases.
 */
fun Activity.setupOverrideConfiguration(overrideConfiguration: Configuration?): Configuration? {
    if (overrideConfiguration != null) {
        val uiMode = overrideConfiguration.uiMode
        overrideConfiguration.setTo(baseContext.resources.configuration)
        overrideConfiguration.uiMode = uiMode
    }

    return overrideConfiguration
}

const val SMALLEST_WIDTH_600: Int = 600
fun Context.createIconWithResourceCompat(@DrawableRes vectorIconId: Int, @DrawableRes adaptiveIconForegroundId: Int, @DrawableRes adaptiveIconBacgroundId: Int): IconCompat {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val adaptiveIconDrawable = AdaptiveIconDrawable(
            ContextCompat.getDrawable(this, adaptiveIconBacgroundId),
            ContextCompat.getDrawable(this, adaptiveIconForegroundId)
        )

        IconCompat.createWithAdaptiveBitmap(
            adaptiveIconDrawable.toBitmap(
                config = Bitmap.Config.ARGB_8888
            )
        )
    } else {
        IconCompat.createWithResource(this, vectorIconId)
    }
}

/**
 * Navigates to the current Google user's account
 * subscriptions in Google Play app.
 */
fun Activity.openGooglePlaySubscriptions() {
    val uriString = "https://play.google.com/store/account/subscriptions"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
    startActivity(intent)
}
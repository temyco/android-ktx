package co.temy.android.ktx.view

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.view.SupportMenuInflater
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import androidx.core.text.getSpans
import androidx.core.text.toSpannable
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.core.view.doOnPreDraw
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.math.min

fun EditText.value() = text.toString()

inline fun EditText.setOnEditorActionListener(crossinline onAction: (Int) -> Boolean) {
    setOnEditorActionListener { _, actionId, _ -> onAction(actionId) }
}

inline fun EditText.setOnDoneEditorActionListener(crossinline onAction: (Int) -> Unit = {}) {
    setOnEditorActionListener { actionId ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onAction(actionId)
            true
        } else false
    }
}

/**
 * Runs [onAction] block and hides a keyboard when [EditorInfo.IME_ACTION_DONE] is fired.
 *
 * @param onAction function to be called when [[EditorInfo.IME_ACTION_DONE] is fired
 */
inline fun EditText.setHideKeyboardEditorActionListener(crossinline onAction: (Int) -> Unit = {}) {
    setOnDoneEditorActionListener {
        hideKeyboard()
        onAction(it)
    }
}

fun View.showKeyboard() {
    val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun View.hideKeyboard() {
    val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(windowToken, 0)
    this.clearFocus()
}

fun Fragment.showKeyboard() {
    val imm: InputMethodManager? = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun Fragment.hideKeyboard() {
    val view = view?.findFocus() ?: return
    val imm: InputMethodManager? = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}

fun Context.openBrowser(url: String, clearFromRecent: Boolean = true) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    if (clearFromRecent) browserIntent.flags = browserIntent.flags or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
    startActivity(browserIntent)
}

/**
 * @return [View.VISIBLE] if Boolean value is true, [View.GONE] otherwise.
 */
fun Boolean.asVisibleOrGoneFlag() = if (this) View.VISIBLE else View.GONE

/**
 * @return [View.GONE] if Boolean value is true, [View.VISIBLE] otherwise.
 */
fun Boolean.asGoneOrVisibleFlag() = if (this) View.GONE else View.VISIBLE

/**
 * @return [View.VISIBLE] if Boolean value is true, [View.INVISIBLE] otherwise.
 */
fun Boolean.asVisibleOrInvisibleFlag() = if (this) View.VISIBLE else View.INVISIBLE

/**
 * Resets nested vertical scroll position.
 *
 * Can be used to reset [CoordinatorLayout]'s behaviours that depends on scrolling views.
 */
fun RecyclerView.resetNestedVerticalScroll() {
    startNestedScroll(androidx.core.view.ViewCompat.SCROLL_AXIS_VERTICAL)
    dispatchNestedPreScroll(0, -java.lang.Integer.MAX_VALUE, null, null)
    dispatchNestedScroll(0, -java.lang.Integer.MAX_VALUE, 0, 0, null)
    stopNestedScroll()
}

fun LinearLayoutManager.getCurrentPosition(midScreenX: Int, midScreenY: Int): Int = when(this.orientation) {
    LinearLayoutManager.HORIZONTAL -> getCurrentPositionForHorizontalOrientation(midScreenX)
    LinearLayoutManager.VERTICAL -> getCurrentPositionForVerticalOrientation(midScreenY)
    else -> RecyclerView.NO_POSITION
}

/**
 * Get current position for [LinearLayoutManager.HORIZONTAL]
 *
 * @return position of center item if it exists else
 * @return first completely visible or first visible item position
 *
 * @return [RecyclerView.NO_POSITION] if [LinearLayoutManager] doesn't have horizontal orientation
 */
private fun LinearLayoutManager.getCurrentPositionForHorizontalOrientation(midScreenX: Int) : Int {
    if (this.orientation == LinearLayoutManager.HORIZONTAL) {
        val centerPosition = getCenterPositionForHorizontalOrientation(midScreenX)
        return if (centerPosition == RecyclerView.NO_POSITION) {
            getCompletelyVisibleOrFirstPosition()
        } else centerPosition
    }

    return RecyclerView.NO_POSITION
}

private fun LinearLayoutManager.getCompletelyVisibleOrFirstPosition(): Int {
    val firstCompletelyVisiblePosition = findFirstCompletelyVisibleItemPosition()

    return if (firstCompletelyVisiblePosition == RecyclerView.NO_POSITION) {
        findFirstVisibleItemPosition()
    } else {
        firstCompletelyVisiblePosition
    }
}

/**
 * Get position of center item for [LinearLayoutManager.HORIZONTAL]
 *
 * @return position of item with start X less than middle of the screen and end X more than middle of the screen
 *
 * @return [RecyclerView.NO_POSITION] if [LinearLayoutManager] doesn't have horizontal orientation or there is no item that meets requirements
 */
private fun LinearLayoutManager.getCenterPositionForHorizontalOrientation(midScreenX: Int) : Int {
    if (this.orientation == LinearLayoutManager.HORIZONTAL) {
        val firstPosition = this.findFirstVisibleItemPosition()
        val lastPosition = this.findLastVisibleItemPosition()

        for (position in firstPosition..lastPosition) {
            val view = this.findViewByPosition(position)
            if (view != null) {
                val viewWidth = view.measuredWidth
                val viewStartX = view.x
                val viewEndX = viewStartX + viewWidth
                if (viewStartX < midScreenX && viewEndX > midScreenX) {
                    return position
                }
            }
        }

        return RecyclerView.NO_POSITION
    }

    return RecyclerView.NO_POSITION
}

/**
 * Get current position for [LinearLayoutManager.VERTICAL]
 *
 * @return position of center item if it exists else
 * @return first completely visible or first visible item position
 *
 * @return [RecyclerView.NO_POSITION] if [LinearLayoutManager] doesn't have vertical orientation
 */

private fun LinearLayoutManager.getCurrentPositionForVerticalOrientation(midScreenY: Int): Int {
    if (this.orientation == LinearLayoutManager.VERTICAL) {
        val centerPosition = getCenterPositionForVerticalOrientation(midScreenY)
        return if (centerPosition == RecyclerView.NO_POSITION) {
            getCompletelyVisibleOrFirstPosition()
        } else centerPosition
    }

    return RecyclerView.NO_POSITION
}

/**
 * Get position of center item for [LinearLayoutManager.VERTICAL]
 *
 * @return position of item with start Y less than middle of the screen and end Y more than middle of the screen
 *
 * @return [RecyclerView.NO_POSITION] if [LinearLayoutManager] doesn't have vertical orientation or there is no item that meets requirements
 */
private fun LinearLayoutManager.getCenterPositionForVerticalOrientation(midScreenY: Int) : Int {
    if (this.orientation == LinearLayoutManager.VERTICAL) {
        val firstPosition = this.findFirstVisibleItemPosition()
        val lastPosition = this.findLastVisibleItemPosition()

        for (position in firstPosition..lastPosition) {
            val view = this.findViewByPosition(position)
            if (view != null) {
                val viewHeight = view.measuredHeight
                val viewStartY = view.y
                val viewEndY= viewStartY + viewHeight
                if (viewStartY < midScreenY && viewEndY > midScreenY) {
                    return position
                }
            }
        }

        return RecyclerView.NO_POSITION
    }

    return RecyclerView.NO_POSITION
}

fun View.setBottomPadding(bottomPadding: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, bottomPadding)
}

fun Context.inflate(resource: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this).inflate(resource, root, attachToRoot)
}

fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.getDrawableCompat(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)

/**
 * Triggers when recycler view state changes to [RecyclerView.SCROLL_STATE_IDLE].
 */
inline fun RecyclerView.addOnIdleStateListener(crossinline listener: (RecyclerView) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                listener.invoke(recyclerView)
            }
        }
    })
}

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

fun ViewGroup.LayoutParams.calculateAspectRatio(
    maxWidth: Float,
    maxHeight: Float,
    imageWidth: Float,
    imageHeight: Float
): ViewGroup.LayoutParams = apply {
    val widthRatio = maxWidth / imageWidth
    val heightRatio = maxHeight / imageHeight
    val bestRatio = min(widthRatio, heightRatio)

    this.width = (imageWidth * bestRatio).toInt()
    this.height = (imageHeight * bestRatio).toInt()
}

fun ViewGroup.LayoutParams.calculateProportionalHeight(
    maxWidth: Float,
    imageWidth: Float,
    imageHeight: Float
): ViewGroup.LayoutParams = apply {
    val aspectRatio = maxWidth / imageWidth
    this.height = (imageHeight * aspectRatio).toInt()
}

fun View.setProportionalHeight(imageWidth: Float, imageHeight: Float) {
    doOnPreDraw {
        val maxWidth = it.width.toFloat()
        it.layoutParams = it.layoutParams.calculateProportionalHeight(maxWidth, imageWidth, imageHeight)
    }
}

fun View.setProportionalAspectRatio(imageWidth: Float, imageHeight: Float) {
    doOnPreDraw {
        val maxWidth = it.width.toFloat()
        val maxHeight = it.height.toFloat()
        it.layoutParams = it.layoutParams.calculateAspectRatio(maxWidth, maxHeight, imageWidth, imageHeight)
    }
}

fun View.setElevationCompat(value: Float) {
    ViewCompat.setElevation(this, value)
}

fun View.getElevationCompat() = ViewCompat.getElevation(this)

fun View.enableChildrenViews(enable: Boolean) {
    this.isEnabled = enable
    if (this is ViewGroup) {
        this.children.forEach { it.enableChildrenViews(enable) }
    }
}

/**
 * Attaches Live data to Edit Text.
 * This will update Live Data data with all text changes from Edit Text.
 */
fun <T> AutoCompleteTextView.attachLiveData(data: MutableLiveData<T>) {
    setOnItemClickListener { parent, view, position, id ->
        data.value = parent.adapter.getItem(position) as T
    }
}


/**
 * Attaches Live data to Edit Text.
 * This will update Live Data data with all text changes from Edit Text.
 */
fun EditText.attachLiveData(data: MutableLiveData<String>) {
    data.value = text.toString()
    addTextChangedListener {
        data.value = it.toString()
    }
}

/**
 * Attaches Live data to Check Box.
 * This will update Live Data data with all state changes from Check Box.
 */
fun CheckBox.attachLiveData(data: MutableLiveData<Boolean>) {
    data.value = isChecked
    setOnCheckedChangeListener { _, isChecked ->
        data.value = isChecked
    }
}

/**
 * Attaches Live data to Radio Group.
 * This will update Live Data data with selected radio button text.
 */
fun RadioGroup.attachLiveDataForValue(data: MutableLiveData<String>) {
    data.value = getCheckedView()?.text.toString()
    setOnCheckedChangeListener { group, checkedId ->
        val checkedView = group.findViewById<RadioButton>(checkedId)
        data.value = checkedView.text.toString()
    }
}

/**
 * Attaches Live data to Radio Group.
 * This will update Live Data data with selected radio button text.
 */
fun RadioGroup.attachLiveDataForId(data: MutableLiveData<Int>) {
    data.value = checkedRadioButtonId
    setOnCheckedChangeListener { group, checkedId ->
        data.value = checkedId
    }
}

inline fun EditText.addTextChangedListener(crossinline onTextChanged: (text: CharSequence?) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChanged.invoke(p0)
        }
    })
}

fun RadioGroup.getCheckedView() = findViewById<RadioButton>(checkedRadioButtonId) ?: null

fun TextView.setTextAppearanceCompat(appearanceId: Int) {
    TextViewCompat.setTextAppearance(this, appearanceId)
}

/**
 * Returns [Spannable] where the term is
 * highlighted as a bold text.
 *
 * Use it to highlight a search query in the result string.
 */
fun String.highlightTerm(term: String): SpannableString {
    val regex = Regex(term.toLowerCase())
    val matches = regex.findAll(this.toLowerCase())
    val ranges = matches.map { it.range }
    val spannable = SpannableString(this)
    ranges.forEach {
        spannable.setSpan(ForegroundColorSpan(Color.BLACK), it.start, it.endInclusive + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    return spannable
}

/**
 * Returns [Spannable] where all <em> (e.g. italic) tags are replaced
 * with <strong> (e.g. bold)
 *
 * Use it to show a search result for /pages response.
 *
 * Important: works only with <em> tags replacing it only with <strong> tags.
 * Using deprecated [Html.fromHtml] as it's deprecated only from API 24.
 */
fun String.replaceItalicWithBold(): SpannableString {
    val spannable = SpannableString(Html.fromHtml(this))

    spannable.getSpans<StyleSpan>(0, this.lastIndex)
        .replaceItalicInSpannable(spannable)

    return spannable
}

fun Array<out StyleSpan>.replaceItalicInSpannable(spannable: SpannableString) = forEach {
    if (it.style == Typeface.ITALIC) {
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            spannable.getSpanStart(it),
            spannable.getSpanEnd(it),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(Color.BLACK),
            spannable.getSpanStart(it),
            spannable.getSpanEnd(it),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.removeSpan(it)
    }
}

fun ActionMenuView.inflateMenu(menuId: Int) = SupportMenuInflater(context).inflate(menuId, menu)

fun Intent.getNullableIntExtra(name: String, default: Int? = null): Int? {
    val extra = getIntExtra(name, -1)
    return if (extra == -1) default else extra
}

fun Context.currentLanguage() = ConfigurationCompat.getLocales(resources.configuration)[0].language

fun Window.disableScreenshots() {
    this.setFlags(
        WindowManager.LayoutParams.FLAG_SECURE,
        WindowManager.LayoutParams.FLAG_SECURE
    )
}

fun Snackbar.centerMessage(): Snackbar = apply {
    val textView = this.view.findViewById<AppCompatTextView>(com.google.android.material.R.id.snackbar_text)
    textView.textAlignment = android.view.View.TEXT_ALIGNMENT_CENTER
}

fun Snackbar.show(isCentered: Boolean) {
    if (isCentered) {
        this.centerMessage()
    }

    this.show()
}

fun String.makeTextClickable(clickableText: String, clickableTextColor: Int, clickListener: () -> (Unit)): SpannableString {
    val regex = Regex(clickableText)
    val matches = regex.findAll(this)
    val ranges = matches.map { it.range }
    val spannable = SpannableString(this)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) { clickListener.invoke() }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = clickableTextColor
            ds.isUnderlineText = false
        }
    }
    ranges.forEach {
        spannable.setSpan(clickableSpan, it.first, it.last + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    return spannable
}

fun Spannable.makeTextClickable(clickableText: String, clickableTextColor: Int, clickListener: () -> (Unit)): SpannableString {
    val regex = Regex(clickableText)
    val matches = regex.findAll(this)
    val ranges = matches.map { it.range }
    val spannable = SpannableString(this)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) { clickListener.invoke() }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = clickableTextColor
            ds.isUnderlineText = false
        }
    }
    ranges.forEach {
        spannable.setSpan(clickableSpan, it.first, it.last + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    return spannable
}

/**
 * Searches for [clickableText] inside the TextView's text property.
 * If found, highlights [clickableText] with [clickableTextColor] and sets [clickListener] to it.
 *
 * @param clickableText text that must be present inside TextView's text property
 * @param clickableTextColor color for highlighting [clickableText]
 * @param clickListener functions that will we invoked on [clickableText] click
 */
fun TextView.makeTextClickable(clickableText: String, clickableTextColor: Int, clickListener: () -> (Unit)) {
    this.movementMethod = LinkMovementMethod.getInstance()
    this.text = this.text.toString().makeTextClickable(clickableText, clickableTextColor, clickListener)
}

fun TextView.makeSpannableTextClickable(clickableText: String, clickableTextColor: Int, clickListener: () -> (Unit)) {
    this.movementMethod = LinkMovementMethod.getInstance()
    this.text = this.text.toSpannable().makeTextClickable(clickableText, clickableTextColor, clickListener)
}

fun View.removeFocus() {
    clearFocus()
    isFocusable = false
    isFocusableInTouchMode = false
}

fun View.setFocus() {
    isFocusable = true
    isFocusableInTouchMode = true
}

fun Boolean.asTextOrNullInputType() = if (this) EditorInfo.TYPE_CLASS_TEXT else EditorInfo.TYPE_NULL
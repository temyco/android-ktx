# Android Kotlin Extensions
Android Kotlin extensions library made by Temy Android developers.

## Extensions Listing

### [Activity Extensions](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt)

- [`getDisplayWidth()`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L12)
- [`enablePortraitScreenOrientationForMobile(isPortrait: Boolean)`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L19)
- [`lockScreenOrientationChanges(lock: Boolean)`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L26)
- [`openEmailClient(emailTo: String, subject: String)`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L30)
- [`openNotificationSettings()`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L43)
- [`setupOverrideConfiguration(overrideConfiguration: Configuration?): Configuration?`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L67)
- [`openGooglePlaySubscriptions()`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L81)

### [Cipher Extensions](app/src/main/java/co/temy/android/ktx/CipherExtensions.kt)

- [`encrypt(data: String, key: Key?, useInitializationVector: Boolean = false): String`](app/src/main/java/co/temy/android/ktx/CipherExtensions.kt#L19)
- [`decrypt(data: String, key: Key?, useInitializationVector: Boolean = false): String`](app/src/main/java/co/temy/android/ktx/CipherExtensions.kt#L43)
- [`wrapKey(keyToBeWrapped: Key, keyToWrapWith: Key?): String`](app/src/main/java/co/temy/android/ktx/CipherExtensions.kt#L67)
- [`unWrapKey(wrappedKeyData: String, algorithm: String, wrappedKeyType: Int, keyToUnWrapWith: Key?): Key`](app/src/main/java/co/temy/android/ktx/CipherExtensions.kt#L77)

### [Context Extensions](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt)

- [`isTabletConfig(): Boolean`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L23)
- [`isPortraitSw600Config(): Boolean`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L25)
- [`isLandscapeSw600Config(): Boolean`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L29)
- [`isLandscapeDisplayOrientation(): Boolean`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L33)
- [`addLifecycleObserver(observer: LifecycleObserver)`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L35)
- [`getMiddleScreenX(): Int`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L43)
- [`getMiddleScreenY(): Int`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L48)
- [`createIconWithResourceCompat(@DrawableRes vectorIconId: Int, @DrawableRes adaptiveIconForegroundId: Int, @DrawableRes adaptiveIconBackgroundId: Int): IconCompat`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L54)
- [`currentLanguage(): String`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L71)
- [`openBrowser(url: String, clearFromRecent: Boolean = true)`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L73)
- [`inflate(resource: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L80)
- [`getColorCompat(@ColorRes color: Int): Int`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L84)
- [`getDrawableCompat(@DrawableRes drawable: Int): Drawable?`](app/src/main/java/co/temy/android/ktx/ActivityExtensions.kt#L86)

### [Fragment Extensions](app/src/main/java/co/temy/android/ktx/FragmentExtensions.kt)

- [`showKeyboard()`](app/src/main/java/co/temy/android/ktx/FragmentExtensions.kt#L7)
- [`hideKeyboard()`](app/src/main/java/co/temy/android/ktx/FragmentExtensions.kt#L12)

### [Metrics Extensions](app/src/main/java/co/temy/android/ktx/MetricsExtensions.kt)

- [`Int.toDp(): Int`](app/src/main/java/co/temy/android/ktx/MetricsExtensions.kt#L9)
- [`Int.toPx(): Int`](app/src/main/java/co/temy/android/ktx/MetricsExtensions.kt#L17)

### [String Extensions](app/src/main/java/co/temy/android/ktx/StringExtensions.kt)

- [`String.highlightTerm(term: String): SpannableString`](app/src/main/java/co/temy/android/ktx/StringExtensions.kt#L19)
- [`String.replaceItalicWithBold(): SpannableString`](app/src/main/java/co/temy/android/ktx/StringExtensions.kt#L43)
- [`Array<out StyleSpan>.replaceItalicInSpannable(spannable: SpannableString)`](app/src/main/java/co/temy/android/ktx/StringExtensions.kt#L52)
- [`CharSequence.makeTextClickable(clickableText: String, clickableTextColor: Int, clickListener: () -> (Unit)): SpannableString `](app/src/main/java/co/temy/android/ktx/StringExtensions.kt#L70)

### [View Extensions](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt)

- [`EditText.value(): String`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L28)
- [EditText.setOnEditorActionListener(crossinline onAction: (Int) -> Boolean)](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L30)
- [`EditText.setOnDoneEditorActionListener(crossinline onAction: (Int) -> Unit = {})`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L34)
- [`EditText.setHideKeyboardEditorActionListener(crossinline onAction: (Int) -> Unit = {})`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L48)
- [`View.showKeyboard()`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L55)
- [`View.hideKeyboard()`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L60)
- [`Boolean.asVisibleOrGoneFlag(): Int`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L70)
- [`Boolean.asGoneOrVisibleFlag(): int`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L75)
- [`Boolean.asVisibleOrInvisibleFlag(): Int`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L80)
- [`RecyclerView.resetNestedVerticalScroll()`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L87)
- [`LinearLayoutManager.getCurrentPosition(midScreenX: Int, midScreenY: Int): Int`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L94)
- [`LinearLayoutManager.getCurrentPositionForHorizontalOrientation(midScreenX: Int) : Int`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L108)
- [`LinearLayoutManager.getCompletelyVisibleOrFirstPosition(): Int`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L119)
- [`LinearLayoutManager.getCenterPositionForHorizontalOrientation(midScreenX: Int) : Int`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L136)
- [`LinearLayoutManager.getCurrentPositionForVerticalOrientation(midScreenY: Int): Int`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L168)
- [`LinearLayoutManager.getCenterPositionForVerticalOrientation(midScreenY: Int) : Int`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L186)
- [`View.setBottomPadding(bottomPadding: Int)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L209)
- [`RecyclerView.addOnIdleStateListener(crossinline listener: (RecyclerView) -> Unit)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L216)
- [`View.setProportionalHeight(imageWidth: Float, imageHeight: Float)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L226)
- [`View.setProportionalAspectRatio(imageWidth: Float, imageHeight: Float)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L233)
- [`View.setElevationCompat(value: Float)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L241)
- [`View.getElevationCompat(): Float`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L245)
- [`View.enableChildrenViews(enable: Boolean)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L247)
- [`<T> AutoCompleteTextView.attachLiveData(data: MutableLiveData<T>)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L258)
- [`EditText.attachLiveData(data: MutableLiveData<String>)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L269)
- [`CheckBox.attachLiveData(data: MutableLiveData<Boolean>)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L280)
- [`EditText.addTextChangedListener(crossinline onTextChanged: (text: CharSequence?) -> Unit)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L287)
- [`TextView.setTextAppearanceCompat(appearanceId: Int)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L301)
- [`Window.disableScreenshots()`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L305)
- [`Snackbar.centerMessage(): Snackbar`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L312)
- [`Snackbar.show(isCentered: Boolean)`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L317)
- [`TextView.makeTextClickable(clickableText: String, clickableTextColor: Int, clickListener: () -> (Unit))`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L333)
- [`TextView.makeSpannableTextClickable(clickableText: String, clickableTextColor: Int, clickListener: () -> (Unit))`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L338)
- [`View.removeFocus()`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L343)
- [`View.setFocus()`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L349)
- [`Boolean.asTextOrNullInputType(): Int`](app/src/main/java/co/temy/android/ktx/ViewExtensions.kt#L354)

### [ViewGroup Extensions](app/src/main/java/co/temy/android/ktx/ViewGroupExtensions.kt)

- [`ViewGroup.LayoutParams.calculateAspectRatio(maxWidth: Float, maxHeight: Float, imageWidth: Float, imageHeight: Float): ViewGroup.LayoutParams`](app/src/main/java/co/temy/android/ktx/ViewGroupExtensions.kt#L11)
- [`ViewGroup.LayoutParams.calculateProportionalHeight(maxWidth: Float, imageWidth: Float, imageHeight: Float): ViewGroup.LayoutParams`](app/src/main/java/co/temy/android/ktx/ViewGroupExtensions.kt#L25)
- [`RadioGroup.attachLiveDataForValue(data: MutableLiveData<String>)`](app/src/main/java/co/temy/android/ktx/ViewGroupExtensions.kt#L38)
- [`RadioGroup.attachLiveDataForId(data: MutableLiveData<Int>)`](app/src/main/java/co/temy/android/ktx/ViewGroupExtensions.kt#L50)
- [`RadioGroup.getCheckedView(): RadioButton?`](app/src/main/java/co/temy/android/ktx/ViewGroupExtensions.kt#L57)
- [`ActionMenuView.inflateMenu(menuId: Int)`](app/src/main/java/co/temy/android/ktx/ViewGroupExtensions.kt#L59)
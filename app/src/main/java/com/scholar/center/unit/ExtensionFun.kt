package com.scholar.center.unit

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService

fun View.closeKeyboard(activity: Activity?) {
    val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun SearchView.openKeyboard(activity: Activity?) {
    val imm = activity?.getSystemService<InputMethodManager>()
    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}


fun Context.getThemeColor(resId: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(
        resId,
        typedValue,
        true
    )
    return typedValue.data
}

fun Int.applyDiscount(discountPercentage: Int): Int {
    val discountAmount = (discountPercentage.toDouble() / 100) * this
    return this - discountAmount.toInt()
}
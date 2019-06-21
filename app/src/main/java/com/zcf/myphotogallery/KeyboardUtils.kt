package com.zcf.myphotogallery

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


fun showSoftKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    imm.showSoftInput(view, 0)
}

fun hideSoftKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
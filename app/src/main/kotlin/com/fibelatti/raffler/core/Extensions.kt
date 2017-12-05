package com.fibelatti.raffler.core

import android.content.SharedPreferences
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

inline fun <T, R> T?.ifNotNullThisElseThat(ifNotNull: (T) -> R, ifNull: () -> R) {
    if (this != null) ifNotNull(this) else ifNull()
}

inline fun <T, R> withItIfNotNull(receiver: T?, block: T.() -> R) {
    receiver?.block()
}

inline fun SharedPreferences.applyWithEditor(func: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    edit().func().apply()
}

fun ViewGroup.inflate(@LayoutRes layoutResource: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutResource, this, attachToRoot)

@JvmOverloads
fun View.snackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

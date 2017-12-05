package com.fibelatti.raffler.core

import android.content.SharedPreferences
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

inline fun <T, R> T?.ifNotNullThisElseThat(ifNotNull: (T) -> R, ifNull: () -> R) {
    if (this != null) ifNotNull(this) else ifNull()
}

inline fun SharedPreferences.applyWithEditor(func: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    edit().func().apply()
}

fun ViewGroup.inflate(@LayoutRes layoutResource: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutResource, this, attachToRoot)

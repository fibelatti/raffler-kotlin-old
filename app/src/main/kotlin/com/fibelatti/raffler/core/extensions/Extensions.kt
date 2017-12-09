package com.fibelatti.raffler.core.extensions

import android.content.SharedPreferences

inline fun SharedPreferences.applyWithEditor(func: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    edit().func().apply()
}

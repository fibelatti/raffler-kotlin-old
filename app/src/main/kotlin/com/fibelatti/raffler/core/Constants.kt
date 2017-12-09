package com.fibelatti.raffler.core

import java.util.*

class Constants {
    companion object {
        const val PLAY_STORE_BASE_URL = "http://play.google.com/store/apps/details"

        const val LOCALE_NONE = "none"
        const val LOCALE_EN = "en"
        const val LOCALE_PT = "pt"
        const val LOCALE_ES = "es"

        val SUPPORTED_LOCALES: List<String> = Arrays.asList(LOCALE_EN, LOCALE_PT, LOCALE_ES)
    }
}

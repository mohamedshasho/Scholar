package com.scholar.center.unit

import android.content.Context
import java.util.Locale


object LocaleHelper {

    @Suppress("DEPRECATION")
    fun setLocale(context: Context, locale: Locale) {
        val config = context.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        context.resources.updateConfiguration(config, null)
    }
}

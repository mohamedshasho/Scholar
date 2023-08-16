package com.scholar.center.unit

import android.content.Context
import androidx.core.text.layoutDirection
import java.util.*


object LocaleHelper {

    fun setLocale(context: Context,locale:Locale) {

        val config = context.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        context.resources.updateConfiguration(config,null)

    }

}

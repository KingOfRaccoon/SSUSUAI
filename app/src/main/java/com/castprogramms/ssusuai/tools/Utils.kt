package com.castprogramms.ssusuai.tools

import android.content.Context
import android.content.res.Configuration

object Utils {
    fun Context.isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}
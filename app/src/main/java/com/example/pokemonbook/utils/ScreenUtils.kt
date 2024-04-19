package com.example.pokemonbook.utils

import android.content.Context


object ScreenUtils {
    fun getScreenWidthDp(context: Context): Float {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels / displayMetrics.density
    }
}
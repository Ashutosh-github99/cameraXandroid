package com.Example.exploringsduicapability.util

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

object HexToJetpackColor {
    val String.color
        get() = Color(android.graphics.Color.parseColor(this))
}

object pesmission{
    public val hasPerm = mutableStateOf(true)
}
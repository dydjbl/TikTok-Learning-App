package com.example.nvapp.Extensions

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import java.text.DecimalFormat


val myFormatted = DecimalFormat("#.#")
fun Long.formattedCount(): String{
    return  if (this < 1000) {
        this.toString()
    } else if (this < 1000000) {
        "${myFormatted.format(this.div(1000))}K"
    } else if (this < 1000000000){
        "${myFormatted.format(this.div(1000000))}M"
    } else {
        "${myFormatted.format(this.div(1000000000))}B"
    }
}

@Composable
fun Dp.Space() = androidx.compose.foundation.layout.Spacer(
    modifier = Modifier.height(this)
)
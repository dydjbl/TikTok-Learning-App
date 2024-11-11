package com.example.nvapp.Extensions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.nvapp.R

enum class LoginOption(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val containerColor: Color = Color.Transparent,
    val contentColor: Color = Color.Black
){
    PHONE_EMAIL(
        icon = R.drawable.ic_profile,
        title = R.string.phoneAndEmail,
    ),
    WEXIN(
        icon = R.drawable.ic_facebook,
        title = R.string.wexin
    ),
    QQ(
      icon = R.drawable.ic_google,
        title = R.string.qq
    )
}
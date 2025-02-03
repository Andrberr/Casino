package com.example.casino.ui.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MenuItemModel(
    @StringRes val titleRes: Int, @DrawableRes val imageRes: Int, val destination: String
)

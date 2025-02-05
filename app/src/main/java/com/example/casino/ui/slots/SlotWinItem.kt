package com.example.casino.ui.slots

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.casino.ui.theme.MenuItemBgColor

@Composable
fun SlotWinItem(symbol: String) {
    Text(
        modifier = Modifier
            .background(MenuItemBgColor)
            .padding(vertical = 10.dp, horizontal = 20.dp),
        text = symbol,
        fontSize = 38.sp,
        color = Color.White
    )
}
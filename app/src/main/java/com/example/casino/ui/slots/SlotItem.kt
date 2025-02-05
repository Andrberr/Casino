package com.example.casino.ui.slots

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SlotItem(symbol: String) {
    Text(
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
        text = symbol,
        fontSize = 38.sp,
        color = Color.White
    )
}
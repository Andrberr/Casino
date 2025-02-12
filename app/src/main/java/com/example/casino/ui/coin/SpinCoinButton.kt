package com.example.casino.ui.coin

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.casino.ui.theme.DiceButtonColor

@Composable
fun SpinCoinButton(titleRes: Int, onClick: () -> Unit) {
    Button(colors = ButtonDefaults.buttonColors(containerColor = DiceButtonColor),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.width(140.dp),
        onClick = {
            onClick()
        }) {
        Text(
            stringResource(titleRes), color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
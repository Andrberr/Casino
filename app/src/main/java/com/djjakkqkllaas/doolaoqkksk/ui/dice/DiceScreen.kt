package com.djjakkqkllaas.doolaoqkksk.ui.dice

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.djjakkqkllaas.doolaoqkksk.R
import com.djjakkqkllaas.doolaoqkksk.ui.composables.TopBar
import com.djjakkqkllaas.doolaoqkksk.ui.noRippleClickable
import com.djjakkqkllaas.doolaoqkksk.ui.theme.DiceButtonColor
import kotlinx.coroutines.launch

@Composable
fun DiceScreen(navController: NavHostController) {

    var balance by remember {
        mutableDoubleStateOf(1000.0)
    }

    var bet by remember {
        mutableIntStateOf(INITIAL_BET)
    }

    var result by remember { mutableIntStateOf(1) }
    var result2 by remember { mutableIntStateOf(2) }
    var result3 by remember { mutableIntStateOf(3) }
    var result4 by remember { mutableIntStateOf(4) }

    var wins1 by remember { mutableIntStateOf(0) }
    var wins2 by remember { mutableIntStateOf(0) }
    var winText by remember {
        mutableStateOf("")
    }

    val rotationAngle = remember { Animatable(0f) }
    val translationX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.dice_bg),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.5f), BlendMode.Multiply)
        )

        TopBar(navController = navController, balance = balance)

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Player 2. Wins: $wins2",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(getDiceImageResource(result)),
                    contentDescription = result.toString(),
                    modifier = Modifier
                        .graphicsLayer(
                            translationX = translationX.value, // Apply shake animation
                            rotationY = rotationAngle.value   // Apply rotation animation
                        )
                        .padding(vertical = 12.dp, horizontal = 6.dp)
                        .size(140.dp)
                )
                Image(
                    painter = painterResource(getDiceImageResource(result2)),
                    contentDescription = result.toString(),
                    modifier = Modifier
                        .graphicsLayer(
                            translationX = translationX.value, // Apply shake animation
                            rotationY = rotationAngle.value   // Apply rotation animation
                        )
                        .padding(vertical = 12.dp, horizontal = 6.dp)
                        .size(140.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    painter = painterResource(getDiceImageResource(result3)),
                    contentDescription = result.toString(),
                    modifier = Modifier
                        .graphicsLayer(
                            translationX = translationX.value, // Apply shake animation
                            rotationY = rotationAngle.value   // Apply rotation animation
                        )
                        .padding(vertical = 12.dp, horizontal = 6.dp)
                        .size(140.dp)
                )
                Image(
                    painter = painterResource(getDiceImageResource(result4)),
                    contentDescription = result.toString(),
                    modifier = Modifier
                        .graphicsLayer(
                            translationX = translationX.value, // Apply shake animation
                            rotationY = rotationAngle.value   // Apply rotation animation
                        )
                        .padding(vertical = 12.dp, horizontal = 6.dp)
                        .size(140.dp)
                )
            }

            Text(
                text = "Player 1. Wins: $wins1",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .offset(y = (-10).dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier
                        .size(22.dp)
                        .noRippleClickable {
                            if (bet - BET_INCREMENT > 0) {
                                bet -= BET_INCREMENT
                            }
                        },
                    imageVector = ImageVector.vectorResource(id = R.drawable.minus_icon),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = bet.toString(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.width(18.dp))

                Image(
                    modifier = Modifier
                        .size(22.dp)
                        .noRippleClickable {
                            if (bet + BET_INCREMENT <= balance && bet + BET_INCREMENT <= MAX_BET) {
                                bet += BET_INCREMENT
                            }
                        },
                    imageVector = ImageVector.vectorResource(id = R.drawable.plus_icon),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Play Button
            Button(colors = ButtonDefaults.buttonColors(containerColor = DiceButtonColor),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.width(160.dp),
                onClick = {
                    winText = ""
                    balance -= bet

                    coroutineScope.launch {
                        launch {
                            translationX.animateTo(
                                targetValue = 50f, // Move to the right
                                animationSpec = tween(durationMillis = 100)
                            )
                            translationX.animateTo(
                                targetValue = -50f, // Move to the left
                                animationSpec = tween(durationMillis = 100)
                            )
                            translationX.animateTo(
                                targetValue = 0f, // Return to center
                                animationSpec = tween(durationMillis = 100)
                            )
                        }

                        rotationAngle.animateTo(
                            targetValue = 360f, // Full rotation

                            animationSpec = tween(durationMillis = 500)
                        )
                        rotationAngle.snapTo(0f)

                        result = (1..6).random()
                        result2 = (1..6).random()
                        result3 = (1..6).random()
                        result4 = (1..6).random()

                        if (result + result2 > result3 + result4) {
                            wins2++
                            winText = "Player 2 won this round!"
                        } else if (result + result2 < result3 + result4) {
                            balance += bet * 2
                            wins1++
                            winText = "Player 1 won this round!"
                        } else {
                            balance += bet
                            winText = "Draw!"
                        }
                    }
                }) {
                Text(
                    stringResource(R.string.Play), color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.height(50.dp),
                text = winText,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun getDiceImageResource(result: Int): Int {
    return when (result) {
        1 -> R.drawable.dice1
        2 -> R.drawable.dice2
        3 -> R.drawable.dice3
        4 -> R.drawable.dice4
        5 -> R.drawable.dice5
        else -> R.drawable.dice6
    }
}

private const val INITIAL_BET = 10
private const val BET_INCREMENT = 10
private const val MAX_BET = 500
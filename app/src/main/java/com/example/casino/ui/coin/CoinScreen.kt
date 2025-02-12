package com.example.casino.ui.coin

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.casino.R
import com.example.casino.ui.composables.TopBar
import com.example.casino.ui.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CoinScreen(navController: NavHostController) {
    var balance by remember {
        mutableDoubleStateOf(1000.0)
    }
    var bet by remember {
        mutableIntStateOf(INITIAL_BET)
    }

    var betSide by remember {
        mutableStateOf<CoinSide?>(null)
    }
    var coinSide by remember {
        mutableStateOf(CoinSide.TAILS)
    }
    var rotationAngle by remember { mutableFloatStateOf(0f) }

    val animatedRotation by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(durationMillis = 2000, easing = LinearOutSlowInEasing), label = ""
    )
    var isRotationCompleted by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        snapshotFlow { animatedRotation }
            .collectLatest { currentRotation ->
                delay(50)
                if (currentRotation == animatedRotation) {
                    if (betSide == coinSide) {
                        balance += bet * 2
                    }
                    isRotationCompleted = true
                }
            }
    }

    LaunchedEffect(animatedRotation) {
        val normalizedAngle = animatedRotation % 360
        coinSide = if (normalizedAngle in 90f..270f) CoinSide.HEADS else CoinSide.TAILS
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.coins_bg),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.5f), BlendMode.Multiply)
        )
        TopBar(navController = navController, balance = balance)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(
                    id = if (coinSide == CoinSide.TAILS) R.drawable.tails else R.drawable.heads
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(260.dp)
                    .graphicsLayer {
                        rotationY = animatedRotation
                        cameraDistance = 8 * density
                    }
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

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                SpinCoinButton(R.string.Heads) {
                    if (isRotationCompleted) {
                        isRotationCompleted = false
                        betSide = CoinSide.HEADS
                        balance -= bet

                        val value = (0..1).random()
                        val angle = (if (value == 0) 180f else 360f) * 5
                        rotationAngle += angle
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                SpinCoinButton(R.string.Tails) {
                    if (isRotationCompleted) {
                        isRotationCompleted = false
                        betSide = CoinSide.TAILS
                        balance -= bet

                        val value = (0..1).random()
                        val angle = (if (value == 0) 180f else 360f) * 5
                        rotationAngle += angle
                    }
                }
            }
        }
    }
}

private const val INITIAL_BET = 10
private const val BET_INCREMENT = 10
private const val MAX_BET = 500



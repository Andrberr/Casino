package com.example.casino.ui.roulette

import SpinButton
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.casino.R
import com.example.casino.ui.Constants.ROULETTE_SLOTS
import com.example.casino.ui.noRippleClickable
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.random.Random

@Composable
fun RouletteScreen(navController: NavHostController) {
    var balance by remember {
        mutableDoubleStateOf(5000.0)
    }

    var bet by remember {
        mutableIntStateOf(100)
    }

    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    fun spinRoulette(spinType: SpinType) {
        if (balance >= bet) {
            balance -= bet
            scope.launch {
                val degree = getSpinDegree()

                rotation.snapTo(0f)
                rotation.animateTo(
                    targetValue = degree + getStartSpinDegree(),
                    animationSpec = tween(
                        durationMillis = 3000,
                        easing = FastOutSlowInEasing
                    )
                )

                val points = getPointsFromSpin(
                    spinType,
                    degree,
                    bet
                )
                balance += points
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.roulette_bg),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.7f), BlendMode.Multiply)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = balance.toString(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Image(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.coin),
                        contentDescription = null
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Image(
                        modifier = Modifier
                            .size(250.dp)
                            .graphicsLayer(rotationZ = rotation.value)
                            .align(Alignment.TopCenter),
                        painter = painterResource(id = R.drawable.roulette),
                        contentDescription = null
                    )

                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.TopCenter)
                            .offset(y = (-14).dp),
                        tint = Color.White
                    )
                }

                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Row(
                        modifier = Modifier
                            .offset(y = (-10).dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            modifier = Modifier
                                .size(22.dp)
                                .noRippleClickable {
                                    if (bet - 100 > 0) {
                                        bet -= 100
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
                                    if (bet + 100 <= balance) {
                                        bet += 100
                                    }
                                },
                            imageVector = ImageVector.vectorResource(id = R.drawable.plus_icon),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    SpinButton(spinType = SpinType.RED) {
                        spinRoulette(SpinType.RED)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    SpinButton(spinType = SpinType.BLACK) {
                        spinRoulette(SpinType.BLACK)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    SpinButton(spinType = SpinType.ZERO) {
                        spinRoulette(SpinType.ZERO)
                    }
                }
            }
        }
    }
}

private fun getStartSpinDegree(): Float {
    return 360f / ROULETTE_SLOTS / 2
}

private fun getSpinDegree(): Float {
    val min = 360f
    val max = 719f
    val degree = Random.nextFloat() * (max - min) + min
    return degree * 2
}

fun getPointsFromSpin(spinType: SpinType, degree: Float, bet: Int): Int {
    val sectors = arrayOf(
        SpinType.BLACK, SpinType.RED, SpinType.BLACK,
        SpinType.RED, SpinType.BLACK, SpinType.RED, SpinType.BLACK, SpinType.RED,
        SpinType.BLACK, SpinType.RED, SpinType.BLACK, SpinType.RED, SpinType.BLACK,
        SpinType.RED, SpinType.BLACK, SpinType.RED, SpinType.BLACK, SpinType.RED,
        SpinType.BLACK, SpinType.RED, SpinType.BLACK, SpinType.RED, SpinType.BLACK,
        SpinType.RED, SpinType.BLACK, SpinType.RED, SpinType.BLACK, SpinType.RED,
        SpinType.BLACK, SpinType.RED, SpinType.BLACK, SpinType.RED, SpinType.BLACK,
        SpinType.RED, SpinType.BLACK, SpinType.RED, SpinType.ZERO
    )
    val degreesPerSector = 360f / ROULETTE_SLOTS
    val finalAngle = degree % 360
    val sectorIndex = floor(finalAngle / degreesPerSector).toInt()
    val sector = sectors[sectorIndex]


    return when (spinType) {
        SpinType.RED -> {
            if (sector == SpinType.RED) bet * 2
            else 0
        }

        SpinType.BLACK -> {
            if (sector == SpinType.BLACK) bet * 2
            else 0
        }

        SpinType.ZERO -> {
            if (sector == SpinType.ZERO) bet * ROULETTE_SLOTS
            else 0
        }
    }
}
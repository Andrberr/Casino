package com.example.casino.ui.slots

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.casino.R
import com.example.casino.ui.Constants.ITEMS_IN_SLOT
import com.example.casino.ui.composables.TopBar
import com.example.casino.ui.noRippleClickable
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun SlotsScreen(navController: NavHostController) {

    var balance by remember {
        mutableDoubleStateOf(5000.0)
    }

    var bet by remember {
        mutableIntStateOf(100)
    }

    val slotState1 = rememberLazyListState()
    val slotState2 = rememberLazyListState()
    val slotState3 = rememberLazyListState()

    val scope = rememberCoroutineScope()

    val slotSymbols1 = remember {
        getSlotSymbols()
    }
    val slotSymbols2 = remember {
        getSlotSymbols()
    }
    val slotSymbols3 = remember {
        getSlotSymbols()
    }

    val winSymbols1 = remember {
        mutableStateListOf<Int>()
    }
    val winSymbols2 = remember {
        mutableStateListOf<Int>()
    }
    val winSymbols3 = remember {
        mutableStateListOf<Int>()
    }

    fun checkLineForMatches(
        itemNumber1: Int,
        itemNumber2: Int,
        itemNumber3: Int
    ) {
        if (slotSymbols1[itemNumber1] == slotSymbols2[itemNumber2] && slotSymbols1[itemNumber1] == slotSymbols3[itemNumber3]) {
            winSymbols1.add(itemNumber1)
            winSymbols2.add(itemNumber2)
            winSymbols3.add(itemNumber3)
            balance += bet * 3
        } else if (slotSymbols1[itemNumber1] == slotSymbols2[itemNumber2]) {
            winSymbols1.add(itemNumber1)
            winSymbols2.add(itemNumber2)
            balance += bet * 0.3
        } else if (slotSymbols1[itemNumber1] == slotSymbols3[itemNumber3]) {
            winSymbols1.add(itemNumber1)
            winSymbols3.add(itemNumber3)
            balance += bet * 0.3
        } else if (slotSymbols2[itemNumber2] == slotSymbols3[itemNumber3]) {
            winSymbols2.add(itemNumber2)
            winSymbols3.add(itemNumber3)
            balance += bet * 0.3
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.slots_bg),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.5f), BlendMode.Multiply)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            TopBar(navController = navController, balance = balance)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                SlotColumn(slotState1, slotSymbols1, winSymbols1)
                SlotColumn(slotState2, slotSymbols2, winSymbols2)
                SlotColumn(slotState3, slotSymbols3, winSymbols3)
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-10).dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {

                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .offset(y = (-4).dp)
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
                        .size(24.dp)
                        .offset(y = (-4).dp)
                        .noRippleClickable {
                            if (bet + 100 <= balance) {
                                bet += 100
                            }
                        },
                    imageVector = ImageVector.vectorResource(id = R.drawable.plus_icon),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(30.dp))

                Image(
                    modifier = Modifier
                        .size(90.dp)
                        .offset(y = 24.dp)
                        .noRippleClickable {
                            if (balance >= bet) {
                                balance -= bet

                                val itemNumber1 = getItemNumber()
                                val itemNumber2 = getItemNumber()
                                val itemNumber3 = getItemNumber()

                                winSymbols1.clear()
                                winSymbols2.clear()
                                winSymbols3.clear()

                                checkLineForMatches(
                                    itemNumber1,
                                    itemNumber2,
                                    itemNumber3
                                )
                                checkLineForMatches(
                                    itemNumber1 + 1,
                                    itemNumber2 + 1,
                                    itemNumber3 + 1
                                )
                                checkLineForMatches(
                                    itemNumber1 + 2,
                                    itemNumber2 + 2,
                                    itemNumber3 + 2
                                )

                                scope.launch {
                                    slotState1.animateScrollToItem(itemNumber1)
                                }
                                scope.launch {
                                    slotState2.animateScrollToItem(itemNumber2)
                                }
                                scope.launch {
                                    slotState3.animateScrollToItem(itemNumber3)
                                }
                            }
                        },
                    painter = painterResource(id = R.drawable.spin_btn),
                    contentDescription = null
                )
            }
        }
    }
}

private fun getItemNumber(): Int {
    return Random.nextInt(0, ITEMS_IN_SLOT - 2)
}

private fun getSlotSymbols(): List<String> {
    val symbols = listOf("🍒", "🍉", "🍇", "🍑", "🍍")
    val formattedList = symbols.shuffled()
    val resultList = mutableListOf<String>()
    for (i in 1..ITEMS_IN_SLOT / symbols.size) {
        resultList.addAll(formattedList)
    }
    return resultList
}

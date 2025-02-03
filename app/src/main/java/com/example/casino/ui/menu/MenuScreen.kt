package com.example.casino.ui.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.casino.R
import com.example.casino.ui.models.Destinations
import com.example.casino.ui.models.MenuItemModel
import com.example.casino.ui.theme.GamesTitleColor

@Composable
fun MenuScreen(navController: NavHostController) {
    val menuItems = listOf(
        MenuItemModel(R.string.Slots, R.drawable.slots, Destinations.SLOTS_ROUTE),
        MenuItemModel(
            R.string.Roulette,
            R.drawable.roulette,
            Destinations.ROULETTE_ROUTE
        ),
        MenuItemModel(R.string.Dice, R.drawable.dice, Destinations.DICE_ROUTE),
        MenuItemModel(
            R.string.Black_Jack,
            R.drawable.black_jack,
            Destinations.BLACK_JACK_ROUTE
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.casino_bg),
                contentScale = ContentScale.Crop
            )
            .padding(8.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.Games),
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = GamesTitleColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(menuItems) {
                MenuItem(menuItemModel = it, onClick = {
                    navController.navigate(it.destination)
                }
                )
            }
        }
    }
}
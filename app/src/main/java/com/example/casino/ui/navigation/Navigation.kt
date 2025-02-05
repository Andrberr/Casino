package com.example.casino.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.casino.ui.dice.DiceScreen
import com.example.casino.ui.menu.MenuScreen
import com.example.casino.ui.models.Destinations
import com.example.casino.ui.roulette.RouletteScreen
import com.example.casino.ui.slots.SlotsScreen

@Composable
fun Navigation(
    navController: NavHostController,
    setLandscapeOrientation: () -> Unit,
    setDefaultOrientation: () -> Unit
) {

    NavHost(navController, startDestination = Destinations.MenuDestination.route) {

        composable(
            route = Destinations.MenuDestination.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }) {
            setDefaultOrientation()
            MenuScreen(
                navController = navController
            )
        }

        composable(
            route = Destinations.SlotsDestination.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }) {
            setLandscapeOrientation()
            SlotsScreen(
                navController = navController
            )
        }

        composable(
            route = Destinations.RouletteDestination.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }) {
            RouletteScreen(
                navController = navController
            )
        }

        composable(
            route = Destinations.DiceDestination.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }) {
            DiceScreen(
                navController = navController
            )
        }
    }
}

package com.djjakkqkllaas.doolaoqkksk.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.djjakkqkllaas.doolaoqkksk.ui.coin.CoinScreen
import com.djjakkqkllaas.doolaoqkksk.ui.dice.DiceScreen
import com.djjakkqkllaas.doolaoqkksk.ui.menu.MenuScreen
import com.djjakkqkllaas.doolaoqkksk.ui.models.Destinations
import com.djjakkqkllaas.doolaoqkksk.ui.roulette.RouletteScreen
import com.djjakkqkllaas.doolaoqkksk.ui.slots.SlotsScreen

@Composable
fun Navigation(
    navController: NavHostController,
    setLandscapeOrientation: () -> Unit,
    setDefaultOrientation: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
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
                navController = navController,
                onPrivacyPolicyClick = onPrivacyPolicyClick
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
            setLandscapeOrientation()
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

        composable(
            route = Destinations.CoinDestination.route,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }) {
            CoinScreen(
                navController = navController
            )
        }
    }
}

package com.example.casino.ui.models

sealed class Destinations(
    val route: String
) {

    data object MenuDestination : Destinations(
        route = MENU_ROUTE
    )

    data object SlotsDestination : Destinations(
        route = SLOTS_ROUTE
    )

    data object RouletteDestination : Destinations(
        route = ROULETTE_ROUTE
    )

    data object DiceDestination : Destinations(
        route = DICE_ROUTE
    )

    data object BlackJackDestination : Destinations(
        route = BLACK_JACK_ROUTE
    )

    companion object {
        // Routes
        const val MENU_ROUTE = "menu_screen"
        const val SLOTS_ROUTE = "slots_screen"
        const val ROULETTE_ROUTE = "roulette_screen"
        const val DICE_ROUTE = "dice_screen"
        const val BLACK_JACK_ROUTE = "black_jack_screen"
    }
}

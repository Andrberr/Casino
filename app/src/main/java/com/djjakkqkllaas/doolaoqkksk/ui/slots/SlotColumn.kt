package com.djjakkqkllaas.doolaoqkksk.ui.slots

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable

@Composable
fun SlotColumn(state: LazyListState, slotSymbols: List<String>, winSymbols: List<Int>) {
    LazyColumn(
        state = state
    ) {
        itemsIndexed(slotSymbols) { index, symbol ->
            if (winSymbols.contains(index)) {
                SlotWinItem(symbol)
            } else {
                SlotItem(symbol)
            }
        }
    }
}
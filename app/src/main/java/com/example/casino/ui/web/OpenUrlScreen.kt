package com.example.casino.ui.web

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun OpenUrlScreen(url: String) {
    var useWebView by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(url) {
        try {
            val intent = CustomTabsIntent.Builder().build()
            intent.intent.setPackage("com.android.chrome")
            intent.launchUrl(context, Uri.parse(url))
        } catch (e: Exception) {
            useWebView = true
        }
    }

    if (useWebView) {
        WebViewScreen(url)
    }
}
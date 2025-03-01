package com.djjakkqkllaas.doolaoqkksk

import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.djjakkqkllaas.doolaoqkksk.ui.Constants
import com.djjakkqkllaas.doolaoqkksk.ui.navigation.Navigation
import com.djjakkqkllaas.doolaoqkksk.ui.theme.CasinoTheme
import com.djjakkqkllaas.doolaoqkksk.ui.web.OpenUrlScreen
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class MainActivity : ComponentActivity() {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = Constants.REMOTE_CONFIG_FETCH_INTERVAL
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            getFCMToken()
        }
    }
    private val db = Firebase.firestore

    private var showBrowser = false
    private var openedContentUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val fetchedContentUrl = remoteConfig.getString(Constants.CONTENT_URL_KEY)

                    setContent {
                        CasinoTheme {
                            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                Box(modifier = Modifier.padding(innerPadding)) {
                                    val viewModel: MainViewModel = viewModel()
                                    val currentMode = viewModel.getMode(context)

                                    val (mode, contentUrl) = if (currentMode.isEmpty()) {
                                        val mode =
                                            if (fetchedContentUrl.isEmpty()) Constants.CASINO_MODE else Constants.CONTENT_MODE
                                        viewModel.saveMode(context, mode)
                                        viewModel.saveContentUrl(context, fetchedContentUrl)
                                        mode to fetchedContentUrl
                                    } else {
                                        currentMode to viewModel.getContentUrl(
                                            context
                                        )
                                    }

                                    var privacyPolicyUrl by remember {
                                        mutableStateOf("")
                                    }
                                    if (privacyPolicyUrl.isNotEmpty()) {
                                        OpenUrlScreen(url = privacyPolicyUrl)
                                        privacyPolicyUrl = ""
                                    }

                                    when (mode) {
                                        Constants.CASINO_MODE -> {
                                            Navigation(
                                                navController = rememberNavController(),
                                                setLandscapeOrientation = {
                                                    requestedOrientation =
                                                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                                                },
                                                setDefaultOrientation = {
                                                    requestedOrientation =
                                                        ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                                                }
                                            ) {
                                                getPrivacyPolicyUrl {
                                                    privacyPolicyUrl = it
                                                }
                                            }

                                            LaunchedEffect(key1 = Unit) {
                                                askNotificationPermission()
                                            }
                                        }

                                        Constants.CONTENT_MODE -> {
                                            openedContentUrl = contentUrl
                                            OpenUrlScreen(url = contentUrl)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }

    override fun onResume() {
        if (openedContentUrl.isNotEmpty()) {
            if (showBrowser) {
                try {
                    val intent = CustomTabsIntent.Builder().build()
                    intent.intent.setPackage("com.android.chrome")
                    intent.launchUrl(this, Uri.parse(openedContentUrl))
                } catch (e: Exception) {
                    val webView = WebView(this).apply {
                        settings.javaScriptEnabled = true
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                request?.url?.let { view?.loadUrl(it.toString()) }
                                return true
                            }
                        }
                        loadUrl(openedContentUrl)
                    }
                    setContentView(webView)
                }
                showBrowser = false
            } else {
                showBrowser = true
                moveTaskToBack(true)
            }
        }
        super.onResume()
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                getFCMToken()
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {

            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            getFCMToken()
        }
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Error get token", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("FCM", "FCM token: $token")
        }
    }

    private fun getPrivacyPolicyUrl(onUrlGet: (String) -> Unit) {
        db.collection(Constants.DB_COLLECTION)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    onUrlGet(document.data[Constants.PRIVACY_POLICY_KEY].toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }
}
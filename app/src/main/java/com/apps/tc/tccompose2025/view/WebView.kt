package com.apps.tc.tccompose2025.view

import android.annotation.SuppressLint
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import java.io.BufferedReader
import java.io.File

/**
 * Web View for opening any external http links
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewExternal(modifier: Modifier, url: String) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        }
    )
}

/**
 * Web View for opening any internally downloaded http files (the internal storage of the app)
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewInternal(modifier: Modifier, url: String) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val fileUri = Uri.fromFile(File(context.filesDir.absolutePath, url))
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(fileUri.toString())
            }
        }
    )
}

/**
 * Web View for opening any html files from the assets
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewAssets(modifier: Modifier, url: String) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
//            val htmlContent = context.assets.open("parigaram/parigaram_1_1.html").bufferedReader().use(
//                BufferedReader::readText
//            )
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true

                //loadData(htmlContent, "text/html", "UTF-8")
                loadUrl("file:///android_asset/$url")
            }
        }
    )
}


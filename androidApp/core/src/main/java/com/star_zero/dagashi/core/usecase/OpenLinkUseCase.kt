package com.star_zero.dagashi.core.usecase

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.platform.UriHandler

class OpenLinkUseCase(
    private val context: Context,
    private val uriHandler: UriHandler
) {

    operator fun invoke(url: String, isOpenLinkInApp: Boolean) {
        if (isOpenLinkInApp) {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        } else {
                uriHandler.openUri(url)
        }
    }
}

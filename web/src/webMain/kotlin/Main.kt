package com.gaokao.web

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.ComposeViewport
import com.gaokao.GaokaoGuideApp
import com.gaokao.platformGetCssVar
import com.gaokao.platformHideLoading

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    platformHideLoading()
    ComposeViewport(viewportContainerId = "composeApp") {
        var insetTopPx by remember { mutableDoubleStateOf(0.0) }
        var insetBottomPx by remember { mutableDoubleStateOf(0.0) }
        var insetStartPx by remember { mutableDoubleStateOf(0.0) }
        var insetEndPx by remember { mutableDoubleStateOf(0.0) }

        LaunchedEffect(Unit) {
            insetTopPx = platformGetCssVar("--safe-area-inset-top")
            insetStartPx = platformGetCssVar("--safe-area-inset-left")
            insetEndPx = platformGetCssVar("--safe-area-inset-right")
            insetBottomPx = platformGetCssVar("--safe-area-inset-bottom")
        }

        val safePadding = PaddingValues(
            top = Dp(insetTopPx.toFloat()),
            bottom = Dp(insetBottomPx.toFloat()),
            start = Dp(insetStartPx.toFloat()),
            end = Dp(insetEndPx.toFloat()),
        )

        GaokaoGuideApp(padding = safePadding)
    }
}

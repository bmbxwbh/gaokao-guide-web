package com.gaokao.web

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.ComposeViewport
import com.gaokao.GaokaoGuideApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(viewportContainerId = "composeApp") {
        var insetTopPx by remember { mutableDoubleStateOf(0.0) }
        var insetBottomPx by remember { mutableDoubleStateOf(0.0) }
        var insetStartPx by remember { mutableDoubleStateOf(0.0) }
        var insetEndPx by remember { mutableDoubleStateOf(0.0) }

        LaunchedEffect(Unit) {
            insetTopPx = getCssVar("--safe-area-inset-top")
            insetStartPx = getCssVar("--safe-area-inset-left")
            insetEndPx = getCssVar("--safe-area-inset-right")
            insetBottomPx = getCssVar("--safe-area-inset-bottom")
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

private fun getCssVar(name: String): Double {
    val value = js("getComputedStyle(document.documentElement).getPropertyValue(name)")
        .toString()
        .trim()
    if (value.endsWith("px")) {
        return value.dropLast(2).toDoubleOrNull() ?: 0.0
    }
    return 0.0
}

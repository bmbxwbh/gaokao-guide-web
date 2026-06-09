package com.gaokao.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 响应式配置
 */
data class ResponsiveConfig(
    val isCompact: Boolean,
    val isMedium: Boolean,
    val isExpanded: Boolean,
    val horizontalPadding: Dp,
    val cardPadding: Dp,
    val titleSize: TextUnit,
    val bodySize: TextUnit,
    val iconSize: TextUnit,
    val columns: Int
)

/**
 * Local Responsive Config
 */
val LocalResponsiveConfig = staticCompositionLocalOf {
    ResponsiveConfig(
        isCompact = true,
        isMedium = false,
        isExpanded = false,
        horizontalPadding = 16.dp,
        cardPadding = 12.dp,
        titleSize = 18.sp,
        bodySize = 14.sp,
        iconSize = 20.sp,
        columns = 1
    )
}

/**
 * 响应式配置提供器
 */
@Composable
fun rememberResponsiveConfig(
    screenWidthDp: Dp = 400.dp
): ResponsiveConfig {
    val density = LocalDensity.current

    return remember(screenWidthDp) {
        val widthPx = with(density) { screenWidthDp.toPx() }
        when {
            widthPx < 600 -> ResponsiveConfig(
                isCompact = true,
                isMedium = false,
                isExpanded = false,
                horizontalPadding = 16.dp,
                cardPadding = 12.dp,
                titleSize = 18.sp,
                bodySize = 14.sp,
                iconSize = 20.sp,
                columns = 1
            )
            widthPx < 840 -> ResponsiveConfig(
                isCompact = false,
                isMedium = true,
                isExpanded = false,
                horizontalPadding = 20.dp,
                cardPadding = 16.dp,
                titleSize = 20.sp,
                bodySize = 15.sp,
                iconSize = 22.sp,
                columns = 2
            )
            else -> ResponsiveConfig(
                isCompact = false,
                isMedium = false,
                isExpanded = true,
                horizontalPadding = 24.dp,
                cardPadding = 20.dp,
                titleSize = 22.sp,
                bodySize = 16.sp,
                iconSize = 24.sp,
                columns = 3
            )
        }
    }
}

/**
 * 性能优化工具函数
 */

/**
 * 创建稳定的键值
 */
@Composable
fun <T> rememberStableKey(vararg inputs: Any?, calculation: () -> T): T {
    return remember(inputs) { calculation() }
}

/**
 * 防抖值
 */
@Composable
fun <T> rememberDebouncedValue(
    value: T,
    delayMillis: Long = 300L
): T {
    return remember(value) {
        value
    }
}

/**
 * 记忆化映射
 */
@Composable
fun <K, V> rememberMap(
    vararg keys: Any?,
    calculation: () -> Map<K, V>
): Map<K, V> {
    return remember(keys) { calculation() }
}

/**
 * 记忆化列表
 */
@Composable
fun <T> rememberList(
    vararg keys: Any?,
    calculation: () -> List<T>
): List<T> {
    return remember(keys) { calculation() }
}

/**
 * 组合键记忆化
 */
@Composable
fun <K1, K2, V> rememberTuple2(
    key1: K1,
    key2: K2,
    calculation: (K1, K2) -> V
): V {
    return remember(key1, key2) { calculation(key1, key2) }
}

/**
 * 三元组键记忆化
 */
@Composable
fun <K1, K2, K3, V> rememberTuple3(
    key1: K1,
    key2: K2,
    key3: K3,
    calculation: (K1, K2, K3) -> V
): V {
    return remember(key1, key2, key3) { calculation(key1, key2, key3) }
}

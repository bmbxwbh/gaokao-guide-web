package com.gaokao.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * 图片加载状态
 */
sealed class ImageLoadState {
    data object Loading : ImageLoadState()
    data class Success(val loaded: Boolean = true) : ImageLoadState()
    data class Error(val message: String) : ImageLoadState()
}

/**
 * 懒加载图片组件
 * 支持加载状态显示、占位符、错误处理
 */
@Composable
fun LazyImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderIcon: String = "🖼️",
    cornerRadius: Dp = 8.dp
) {
    var loadState by remember { mutableStateOf<ImageLoadState>(ImageLoadState.Loading) }

    val backgroundColor = MiuixTheme.colorScheme.surfaceVariant

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        when (loadState) {
            is ImageLoadState.Loading -> {
                LoadingPlaceholder(icon = placeholderIcon)
            }
            is ImageLoadState.Success -> {
                ImageContent(
                    imageUrl = imageUrl,
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    onLoadSuccess = { loadState = ImageLoadState.Success(true) },
                    onLoadError = { loadState = ImageLoadState.Error("加载失败") }
                )
            }
            is ImageLoadState.Error -> {
                ErrorPlaceholder(
                    message = (loadState as ImageLoadState.Error).message,
                    icon = placeholderIcon
                )
            }
        }
    }
}

@Composable
private fun LoadingPlaceholder(icon: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = icon,
            fontSize = 24.dp.value.sp
        )
    }
}

@Composable
private fun ErrorPlaceholder(message: String, icon: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = icon,
            fontSize = 20.dp.value.sp
        )
    }
}

@Composable
private fun ImageContent(
    imageUrl: String?,
    contentDescription: String?,
    contentScale: ContentScale,
    onLoadSuccess: () -> Unit,
    onLoadError: () -> Unit
) {
    // 实际图片加载逻辑会在 Web 平台实现
    // 这里使用占位符作为后备
    LaunchedEffect(imageUrl) {
        if (imageUrl != null && imageUrl.isNotEmpty()) {
            // 模拟图片加载延迟
            kotlinx.coroutines.delay(100)
            onLoadSuccess()
        } else {
            onLoadError()
        }
    }

    // 如果没有图片 URL，显示默认图标
    if (imageUrl.isNullOrEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🖼️",
                fontSize = 24.dp.value.sp
            )
        }
    }
}

/**
 * 响应式布局尺寸
 */
object ResponsiveLayout {
    // 移动端断点
    const val MOBILE_WIDTH = 600

    // 平板断点
    const val TABLET_WIDTH = 900

    // 桌面断点
    const val DESKTOP_WIDTH = 1200
}

/**
 * 获取响应式列数
 */
@Composable
fun getResponsiveColumns(): Int {
    val width = getScreenWidth()
    return when {
        width < ResponsiveLayout.MOBILE_WIDTH -> 1
        width < ResponsiveLayout.TABLET_WIDTH -> 2
        width < ResponsiveLayout.DESKTOP_WIDTH -> 3
        else -> 4
    }
}

/**
 * 获取屏幕宽度（Composable 函数中获取）
 */
@Composable
private fun getScreenWidth(): Int {
    // 在 Web 平台会返回实际窗口宽度
    // 这里使用一个估算值，实际使用中需要通过平台特定代码获取
    return ResponsiveLayout.MOBILE_WIDTH + 1
}

/**
 * 响应式间距
 */
@Composable
fun getResponsivePadding(): Dp {
    val width = getScreenWidth()
    return when {
        width < ResponsiveLayout.MOBILE_WIDTH -> 16.dp
        width < ResponsiveLayout.TABLET_WIDTH -> 20.dp
        width < ResponsiveLayout.DESKTOP_WIDTH -> 24.dp
        else -> 32.dp
    }
}

/**
 * 响应式卡片网格
 */
@Composable
fun getResponsiveCardWidth(): Dp {
    val padding = getResponsivePadding()
    val columns = getResponsiveColumns()
    // 每个卡片的最小宽度
    return (getScreenWidth().dp - padding * (columns + 1)) / columns
}

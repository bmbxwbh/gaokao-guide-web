package com.gaokao.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gaokao.model.MajorScore
import com.gaokao.model.AdmissionProbability
import com.gaokao.ui.Text
import com.gaokao.ui.getProbabilityText
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * 通用分数卡片组件
 */
@Composable
fun ScoreCard(
    universityName: String,
    majorName: String,
    majorScore: MajorScore,
    year: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    matchScore: Int? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        insideMargin = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = universityName,
                        style = MiuixTheme.textStyles.subtitle
                    )
                    Text(
                        text = majorName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = year,
                    style = MiuixTheme.textStyles.paragraph
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (matchScore != null) {
                    StatColumn(
                        label = "匹配度",
                        value = "$matchScore%",
                        isHighlighted = true
                    )
                }
                StatColumn(
                    label = "最低分",
                    value = "${majorScore.lowScore}"
                )
                StatColumn(
                    label = "平均分",
                    value = "${majorScore.avgScore}",
                    isHighlighted = true
                )
                StatColumn(
                    label = "最高分",
                    value = "${majorScore.highScore}"
                )
                StatColumn(
                    label = "省控线",
                    value = "${majorScore.provinceControlLine}"
                )
            }
        }
    }
}

/**
 * 统计列组件
 */
@Composable
fun StatColumn(
    label: String,
    value: String,
    isHighlighted: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MiuixTheme.textStyles.paragraph
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (isHighlighted) MiuixTheme.colorScheme.primary else Color.Unspecified
        )
    }
}

/**
 * 推荐结果卡片
 */
@Composable
fun RecommendationCard(
    universityName: String,
    majorName: String,
    probability: AdmissionProbability,
    matchScore: Int,
    avgScore: Int,
    lowScore: Int,
    aiSuggestion: String? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        insideMargin = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = universityName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = majorName,
                        style = MiuixTheme.textStyles.paragraph
                    )
                }
                ProbabilityBadge(probability = probability)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatColumn(
                    label = "匹配度",
                    value = "$matchScore%",
                    isHighlighted = true
                )
                StatColumn(
                    label = "平均分",
                    value = "$avgScore"
                )
                StatColumn(
                    label = "最低分",
                    value = "$lowScore"
                )
            }

            if (aiSuggestion != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = aiSuggestion,
                    style = MiuixTheme.textStyles.body2,
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary
                )
            }
        }
    }
}

/**
 * 概率标签组件
 */
@Composable
fun ProbabilityBadge(
    probability: AdmissionProbability,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (probability) {
        AdmissionProbability.SAFE, AdmissionProbability.SURE -> {
            MiuixTheme.colorScheme.primaryContainer to MiuixTheme.colorScheme.onPrimaryContainer
        }
        AdmissionProbability.STABLE -> {
            MiuixTheme.colorScheme.secondaryContainer to MiuixTheme.colorScheme.onSecondaryContainer
        }
        AdmissionProbability.STRETCH -> {
            MiuixTheme.colorScheme.tertiaryContainer to MiuixTheme.colorScheme.onTertiaryContainer
        }
        AdmissionProbability.RISKY -> {
            MiuixTheme.colorScheme.errorContainer to MiuixTheme.colorScheme.onErrorContainer
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = getProbabilityText(probability),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

/**
 * 骨架屏卡片
 */
@Composable
fun SkeletonCard(
    modifier: Modifier = Modifier,
    lines: Int = 3
) {
    val shimmerColors = listOf(
        MiuixTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        MiuixTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
        MiuixTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation - 200f, translateAnimation - 200f),
        end = Offset(translateAnimation, translateAnimation)
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        insideMargin = PaddingValues(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    SkeletonLine(brush = brush, width = 120.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    SkeletonLine(brush = brush, width = 180.dp)
                }
                SkeletonLine(brush = brush, width = 40.dp, height = 20.dp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(lines) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        SkeletonLine(brush = brush, width = 40.dp)
                        Spacer(modifier = Modifier.height(4.dp))
                        SkeletonLine(brush = brush, width = 50.dp, height = 18.dp)
                    }
                }
            }
        }
    }
}

@Composable
fun SkeletonLine(
    brush: Brush,
    modifier: Modifier = Modifier,
    width: Dp = 80.dp,
    height: Dp = 16.dp
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(4.dp))
            .background(brush)
    )
}

/**
 * 空状态组件
 */
@Composable
fun EmptyState(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    actionText: String? = null,
    onAction: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(MiuixTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "📭",
                fontSize = 32.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            style = MiuixTheme.textStyles.title3,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            style = MiuixTheme.textStyles.paragraph,
            textAlign = TextAlign.Center,
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary
        )

        if (actionText != null && onAction != null) {
            Spacer(modifier = Modifier.height(24.dp))
            TextButton(
                text = actionText,
                onClick = onAction,
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
    }
}

/**
 * 筛选标签组
 */
@Composable
fun FilterChipGroup(
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = index == selectedIndex
            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) MiuixTheme.colorScheme.primaryContainer else Color.Transparent,
                label = "chipBackground"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) MiuixTheme.colorScheme.onPrimaryContainer else MiuixTheme.colorScheme.onSurface,
                label = "chipText"
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(backgroundColor)
                    .clickable { onSelect(index) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = option,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    color = textColor
                )
            }
        }
    }
}

/**
 * 分页加载指示器
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "加载中...",
            style = MiuixTheme.textStyles.paragraph,
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary
        )
    }
}

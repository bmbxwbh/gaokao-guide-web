package com.gaokao.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gaokao.data.UniversitiesData
import com.gaokao.model.University
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.SmallTitle
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun HomePage(
    onNavigateToUniversities: () -> Unit,
    onNavigateToScores: () -> Unit,
    onNavigateToRecommendation: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToComparison: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeaderSection()
        }

        item {
            QuickAccessSection(
                onNavigateToUniversities = onNavigateToUniversities,
                onNavigateToScores = onNavigateToScores,
                onNavigateToRecommendation = onNavigateToRecommendation
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SecondaryCard(
                    icon = "❤️",
                    title = "收藏",
                    subtitle = "我的收藏",
                    onClick = onNavigateToFavorites,
                    modifier = Modifier.weight(1f)
                )
                SecondaryCard(
                    icon = "⚖️",
                    title = "对比",
                    subtitle = "高校对比",
                    onClick = onNavigateToComparison,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            SmallTitle("热门高校")
        }

        items(UniversitiesData.universities.take(5)) { university ->
            UniversityCard(
                university = university,
                onClick = { }
            )
        }
    }
}

@Composable
private fun HeaderSection() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MiuixTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🎓",
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column {
                Text(
                    text = "蓉城高考指南",
                    style = MiuixTheme.textStyles.title2
                )
                Text(
                    text = "查询成都市区各大高校历年专业录取分数线",
                    style = MiuixTheme.textStyles.paragraph
                )
            }
        }
    }
}

@Composable
private fun QuickAccessSection(
    onNavigateToUniversities: () -> Unit,
    onNavigateToScores: () -> Unit,
    onNavigateToRecommendation: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryCard(
                icon = "🎓",
                title = "高校列表",
                subtitle = "查看所有高校信息",
                onClick = onNavigateToUniversities,
                modifier = Modifier.weight(1f)
            )
            PrimaryCard(
                icon = "📊",
                title = "专业分数",
                subtitle = "历年专业录取分数线",
                onClick = onNavigateToScores,
                modifier = Modifier.weight(1f)
            )
        }
        FullWidthCard(
            icon = "✨",
            title = "智能推荐",
            subtitle = "根据分数推荐合适的高校专业",
            onClick = onNavigateToRecommendation
        )
    }
}

@Composable
private fun PrimaryCard(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        insideMargin = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MiuixTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 12.dp))
            Column {
                Text(
                    text = title,
                    style = MiuixTheme.textStyles.title3
                )
                Text(
                    text = subtitle,
                    style = MiuixTheme.textStyles.paragraph
                )
            }
        }
    }
}

@Composable
private fun FullWidthCard(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        insideMargin = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MiuixTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = icon,
                        fontSize = 24.sp
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                Column {
                    Text(
                        text = title,
                        style = MiuixTheme.textStyles.title3
                    )
                    Text(
                        text = subtitle,
                        style = MiuixTheme.textStyles.paragraph
                    )
                }
            }
            Text(
                text = "→",
                fontSize = 24.sp,
                color = MiuixTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun SecondaryCard(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        insideMargin = PaddingValues(12.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MiuixTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MiuixTheme.textStyles.subtitle
            )
            Text(
                text = subtitle,
                style = MiuixTheme.textStyles.body2,
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary
            )
        }
    }
}

@Composable
fun UniversityCard(
    university: University,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
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
                        text = university.name,
                        style = MiuixTheme.textStyles.title3
                    )
                    Text(
                        text = university.shortName,
                        style = MiuixTheme.textStyles.paragraph
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MiuixTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = getUniversityTypeName(university.type),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MiuixTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            val scores2025 = university.overviewScores["2025"]
            if (scores2025 != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (scores2025.physicsAvg != null) {
                        ScoreIndicator(
                            label = "物理类",
                            value = "${scores2025.physicsAvg}分"
                        )
                    }
                    if (scores2025.historyAvg != null) {
                        ScoreIndicator(
                            label = "历史类",
                            value = "${scores2025.historyAvg}分"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ScoreIndicator(
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MiuixTheme.textStyles.paragraph
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MiuixTheme.colorScheme.primary
        )
    }
}

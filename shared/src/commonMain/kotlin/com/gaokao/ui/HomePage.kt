package com.gaokao.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gaokao.data.UniversitiesData
import com.gaokao.model.University
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.SmallTitle
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.basic.TextButton
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
            SmallTitle("蓉城高考指南")
            Text(
                text = "查询成都市区各大高校历年专业录取分数线",
                style = MiuixTheme.textStyles.paragraph
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                insideMargin = PaddingValues(16.dp),
                onClick = onNavigateToUniversities
            ) {
                Text(
                    text = "高校列表",
                    style = MiuixTheme.textStyles.title3
                )
                Text(
                    text = "查看所有高校信息",
                    style = MiuixTheme.textStyles.paragraph
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                insideMargin = PaddingValues(16.dp),
                onClick = onNavigateToScores
            ) {
                Text(
                    text = "专业分数",
                    style = MiuixTheme.textStyles.title3
                )
                Text(
                    text = "查询历年专业录取分数线",
                    style = MiuixTheme.textStyles.paragraph
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                insideMargin = PaddingValues(16.dp),
                onClick = onNavigateToRecommendation
            ) {
                Text(
                    text = "智能推荐",
                    style = MiuixTheme.textStyles.title3
                )
                Text(
                    text = "根据分数推荐合适的高校专业",
                    style = MiuixTheme.textStyles.paragraph
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    insideMargin = PaddingValues(16.dp),
                    onClick = onNavigateToFavorites
                ) {
                    Text(
                        text = "收藏",
                        style = MiuixTheme.textStyles.subtitle
                    )
                }
                Card(
                    modifier = Modifier.weight(1f),
                    insideMargin = PaddingValues(16.dp),
                    onClick = onNavigateToComparison
                ) {
                    Text(
                        text = "对比",
                        style = MiuixTheme.textStyles.subtitle
                    )
                }
            }
        }

        item {
            SmallTitle("热门高校")
        }

        items(UniversitiesData.universities.take(3)) { university ->
            UniversityCard(
                university = university,
                onClick = { }
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
        modifier = Modifier.fillMaxWidth(),
        insideMargin = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = university.name,
                    style = MiuixTheme.textStyles.title3
                )
                Text(
                    text = university.shortName,
                    style = MiuixTheme.textStyles.paragraph
                )
            }
            TextButton(
                text = getUniversityTypeName(university.type),
                onClick = {},
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        val scores2025 = university.overviewScores["2025"]
        if (scores2025 != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (scores2025.physicsAvg != null) {
                    Column {
                        Text(
                            text = "物理类",
                            style = MiuixTheme.textStyles.paragraph
                        )
                        Text(
                            text = "${scores2025.physicsAvg}分",
                            fontSize = 18.sp,
                            color = MiuixTheme.colorScheme.primary
                        )
                    }
                }
                if (scores2025.historyAvg != null) {
                    Column {
                        Text(
                            text = "历史类",
                            style = MiuixTheme.textStyles.paragraph
                        )
                        Text(
                            text = "${scores2025.historyAvg}分",
                            fontSize = 18.sp,
                            color = MiuixTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

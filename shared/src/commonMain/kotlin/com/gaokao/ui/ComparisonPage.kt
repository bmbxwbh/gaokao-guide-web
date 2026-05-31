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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.basic.SmallTitle
import com.gaokao.ui.Text
import com.gaokao.ui.TextButton
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.data.UniversitiesData
import com.gaokao.model.ComparisonItem

@Composable
fun ComparisonPage(onNavigateToDetail: (String) -> Unit = {}) {
    var comparisonItems by remember {
        mutableStateOf(
            listOf(
                ComparisonItem(id = "cmp-1", type = "university", targetId = "xihua-university", universityId = "xihua-university"),
                ComparisonItem(id = "cmp-2", type = "university", targetId = "chengdu-university", universityId = "chengdu-university")
            )
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = "对比分析")

        if (comparisonItems.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "暂无对比项",
                    modifier = Modifier.fillMaxWidth(),
                    style = MiuixTheme.textStyles.subtitle,
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(comparisonItems, key = { it.id }) { item ->
                    val university = UniversitiesData.getUniversityById(item.targetId)
                    if (university != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onNavigateToDetail(university.id) }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(university.name, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                                        Text(
                                            "${university.location.city ?: ""} · ${getUniversityTypeName(university.type)}",
                                            style = MiuixTheme.textStyles.paragraph
                                        )
                                    }
                                    TextButton(
                                        text = "移除",
                                        onClick = {
                                            comparisonItems = comparisonItems.filterNot { it.id == item.id }
                                        }
                                    )
                                }
                                val scores2025 = university.overviewScores["2025"]
                                if (scores2025 != null) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        if (scores2025.physicsAvg != null) {
                                            Column {
                                                Text("物理类", style = MiuixTheme.textStyles.paragraph)
                                                Text("${scores2025.physicsAvg}分", fontSize = 16.sp, color = MiuixTheme.colorScheme.primary)
                                            }
                                        }
                                        if (scores2025.historyAvg != null) {
                                            Column {
                                                Text("历史类", style = MiuixTheme.textStyles.paragraph)
                                                Text("${scores2025.historyAvg}分", fontSize = 16.sp, color = MiuixTheme.colorScheme.primary)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (comparisonItems.size >= 2) {
                    item {
                        SmallTitle("对比详情")
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                val universities = comparisonItems.mapNotNull { UniversitiesData.getUniversityById(it.targetId) }
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text("学校", modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
                                    universities.forEach { uni ->
                                        Text(uni.shortName, modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text("类型", modifier = Modifier.weight(1f), style = MiuixTheme.textStyles.paragraph)
                                    universities.forEach { uni ->
                                        Text(getUniversityTypeName(uni.type), modifier = Modifier.weight(1f), style = MiuixTheme.textStyles.paragraph)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text("城市", modifier = Modifier.weight(1f), style = MiuixTheme.textStyles.paragraph)
                                    universities.forEach { uni ->
                                        Text(uni.location.city ?: "", modifier = Modifier.weight(1f), style = MiuixTheme.textStyles.paragraph)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text("建校", modifier = Modifier.weight(1f), style = MiuixTheme.textStyles.paragraph)
                                    universities.forEach { uni ->
                                        Text("${uni.foundingYear}年", modifier = Modifier.weight(1f), style = MiuixTheme.textStyles.paragraph)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text("物理均分", modifier = Modifier.weight(1f), style = MiuixTheme.textStyles.paragraph)
                                    universities.forEach { uni ->
                                        val score = uni.overviewScores["2025"]?.physicsAvg
                                        Text(score?.let { "${it}分" } ?: "-", modifier = Modifier.weight(1f), style = MiuixTheme.textStyles.paragraph)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text("历史均分", modifier = Modifier.weight(1f), style = MiuixTheme.textStyles.paragraph)
                                    universities.forEach { uni ->
                                        val score = uni.overviewScores["2025"]?.historyAvg
                                        Text(score?.let { "${it}分" } ?: "-", modifier = Modifier.weight(1f), style = MiuixTheme.textStyles.paragraph)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

package com.gaokao.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gaokao.ui.components.EmptyState
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme
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
            EmptyState(
                title = "暂无对比项",
                description = "从高校列表中添加高校进行对比分析",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    ComparisonTip(count = comparisonItems.size)
                }

                items(comparisonItems, key = { it.id }) { item ->
                    val university = UniversitiesData.getUniversityById(item.targetId)
                    if (university != null) {
                        ComparisonCard(
                            name = university.name,
                            location = "${university.location.city ?: ""} · ${getUniversityTypeName(university.type)}",
                            physicsScore = university.overviewScores["2025"]?.physicsAvg,
                            historyScore = university.overviewScores["2025"]?.historyAvg,
                            foundingYear = university.foundingYear,
                            majorCount = university.majors.size,
                            onClick = { onNavigateToDetail(university.id) },
                            onRemove = { comparisonItems = comparisonItems.filterNot { it.id == item.id } }
                        )
                    }
                }

                if (comparisonItems.size >= 2) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        SmallTitle("对比详情")
                        ComparisonTable(items = comparisonItems)
                    }
                }
            }
        }
    }
}

@Composable
private fun ComparisonTip(count: Int) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "已选择 $count 所高校",
                style = MiuixTheme.textStyles.paragraph
            )
            if (count < 2) {
                Text(
                    text = "至少选择 2 所进行对比",
                    style = MiuixTheme.textStyles.body2,
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary
                )
            } else {
                Text(
                    text = "可对比",
                    style = MiuixTheme.textStyles.body2,
                    color = MiuixTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun ComparisonCard(
    name: String,
    location: String,
    physicsScore: Int?,
    historyScore: Int?,
    foundingYear: Int,
    majorCount: Int,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                        text = name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = location,
                        style = MiuixTheme.textStyles.paragraph
                    )
                }
                TextButton(
                    text = "移除",
                    onClick = onRemove
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoItem(label = "建校", value = "${foundingYear}年")
                InfoItem(label = "专业", value = "$majorCount 个")
                physicsScore?.let { InfoItem(label = "物理", value = "${it}分") }
                historyScore?.let { InfoItem(label = "历史", value = "${it}分") }
            }
        }
    }
}

@Composable
private fun InfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MiuixTheme.textStyles.paragraph
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MiuixTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ComparisonTable(items: List<ComparisonItem>) {
    val universities = items.mapNotNull { UniversitiesData.getUniversityById(it.targetId) }
    val columns = universities.size + 1

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            ComparisonTableRow(
                label = "学校名称",
                values = universities.map { it.name },
                isHeader = true
            )
            ComparisonTableRow(
                label = "院校类型",
                values = universities.map { getUniversityTypeName(it.type) }
            )
            ComparisonTableRow(
                label = "所在城市",
                values = universities.map { it.location.city ?: "-" }
            )
            ComparisonTableRow(
                label = "建校年份",
                values = universities.map { "${it.foundingYear}年" }
            )
            ComparisonTableRow(
                label = "专业数量",
                values = universities.map { "${it.majors.size} 个" }
            )
            universities.firstOrNull()?.overviewScores?.get("2025")?.let {
                ComparisonTableRow(
                    label = "物理均分",
                    values = universities.map { uni ->
                        uni.overviewScores["2025"]?.physicsAvg?.let { "${it}分" } ?: "-"
                    }
                )
                ComparisonTableRow(
                    label = "历史均分",
                    values = universities.map { uni ->
                        uni.overviewScores["2025"]?.historyAvg?.let { "${it}分" } ?: "-"
                    }
                )
            }
        }
    }
}

@Composable
private fun ComparisonTableRow(
    label: String,
    values: List<String>,
    isHeader: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MiuixTheme.textStyles.paragraph,
            fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal
        )
        values.forEach { value ->
            Text(
                text = value,
                modifier = Modifier.weight(1f),
                style = MiuixTheme.textStyles.paragraph,
                fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
                color = if (isHeader) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.onSurface
            )
        }
    }
}

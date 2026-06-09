package com.gaokao.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gaokao.data.UniversitiesData
import com.gaokao.model.MajorScore
import com.gaokao.model.SubjectType
import com.gaokao.ui.components.EmptyState
import com.gaokao.ui.components.FilterChipGroup
import com.gaokao.ui.components.ScoreCard
import com.gaokao.ui.components.SkeletonCard
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun ScoresPage(onNavigateToDetail: (String, String) -> Unit = { _, _ -> }) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedSubjectType by remember { mutableStateOf(SubjectType.PHYSICS) }
    var isLoading by remember { mutableStateOf(false) }

    data class MajorScoreItem(
        val universityId: String,
        val universityName: String,
        val majorId: String,
        val majorName: String,
        val majorScore: MajorScore,
        val year: String
    )

    val scoreItems by remember(searchQuery, selectedSubjectType) {
        derivedStateOf {
            val items = mutableListOf<MajorScoreItem>()
            UniversitiesData.universities.forEach { university ->
                university.majors.forEach { major ->
                    major.scores.forEach { (year, score) ->
                        if (score.subjectType == selectedSubjectType) {
                            val matchesSearch = searchQuery.isBlank() ||
                                university.name.contains(searchQuery, ignoreCase = true) ||
                                major.name.contains(searchQuery, ignoreCase = true)
                            if (matchesSearch) {
                                items.add(
                                    MajorScoreItem(
                                        universityId = university.id,
                                        universityName = university.name,
                                        majorId = major.id,
                                        majorName = major.name,
                                        majorScore = score,
                                        year = year
                                    )
                                )
                            }
                        }
                    }
                }
            }
            items.sortedByDescending { it.majorScore.avgScore }
        }
    }

    val filteredItems by remember(searchQuery, selectedSubjectType, scoreItems) {
        derivedStateOf {
            if (searchQuery.isBlank()) scoreItems
            else scoreItems.filter {
                it.universityName.contains(searchQuery, ignoreCase = true) ||
                    it.majorName.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = "专业分数")

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                placeholder = "搜索高校或专业名称"
            )

            Spacer(modifier = Modifier.height(12.dp))

            FilterChipGroup(
                options = listOf("物理类", "历史类"),
                selectedIndex = if (selectedSubjectType == SubjectType.PHYSICS) 0 else 1,
                onSelect = { index ->
                    selectedSubjectType = if (index == 0) SubjectType.PHYSICS else SubjectType.HISTORY
                }
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (isLoading) {
                items(5) {
                    SkeletonCard()
                }
            } else if (filteredItems.isEmpty()) {
                item {
                    EmptyState(
                        title = "暂无数据",
                        description = "没有找到符合条件的专业分数信息",
                        actionText = "清除筛选",
                        onAction = { searchQuery = "" }
                    )
                }
            } else {
                items(
                    items = filteredItems,
                    key = { "${it.universityId}-${it.majorId}-${it.year}" }
                ) { item ->
                    ScoreCard(
                        universityName = item.universityName,
                        majorName = item.majorName,
                        majorScore = item.majorScore,
                        year = item.year,
                        onClick = { onNavigateToDetail(item.universityId, item.majorId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MiuixTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🔍",
                fontSize = 16.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MiuixTheme.textStyles.paragraph,
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    textStyle = MiuixTheme.textStyles.paragraph.copy(
                        color = MiuixTheme.colorScheme.onSurface
                    ),
                    cursorBrush = SolidColor(MiuixTheme.colorScheme.primary),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (query.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(MiuixTheme.colorScheme.outline)
                        .clickable { onQueryChange("") },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "×",
                        fontSize = 14.sp,
                        color = MiuixTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

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
import com.gaokao.model.SubjectType
import com.gaokao.model.UniversityType
import com.gaokao.ui.components.EmptyState
import com.gaokao.ui.components.FilterChipGroup
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun UniversitiesPage(onNavigateToDetail: (String) -> Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTypeIndex by remember { mutableStateOf(0) }
    var selectedSubjectTypeIndex by remember { mutableStateOf(0) }

    val universityTypes = listOf("全部") + UniversityType.entries.map { getUniversityTypeName(it) }
    val subjectTypes = listOf("全部科类", "物理类", "历史类")

    val filteredUniversities by remember(searchQuery, selectedTypeIndex, selectedSubjectTypeIndex) {
        derivedStateOf {
            UniversitiesData.universities.filter { university ->
                val matchesSearch = searchQuery.isBlank() ||
                    university.name.contains(searchQuery, ignoreCase = true) ||
                    university.shortName.contains(searchQuery, ignoreCase = true)

                val matchesType = selectedTypeIndex == 0 || university.type == UniversityType.entries[selectedTypeIndex - 1]

                val matchesSubject = when (selectedSubjectTypeIndex) {
                    0 -> true
                    1 -> university.overviewScores.values.any { it.physicsAvg != null }
                    2 -> university.overviewScores.values.any { it.historyAvg != null }
                    else -> true
                }

                matchesSearch && matchesType && matchesSubject
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = "高校列表")

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                placeholder = "搜索高校名称"
            )

            Spacer(modifier = Modifier.height(12.dp))

            FilterChipGroup(
                options = universityTypes,
                selectedIndex = selectedTypeIndex,
                onSelect = { selectedTypeIndex = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            FilterChipGroup(
                options = subjectTypes,
                selectedIndex = selectedSubjectTypeIndex,
                onSelect = { selectedSubjectTypeIndex = it }
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (filteredUniversities.isEmpty()) {
                item {
                    EmptyState(
                        title = "暂无高校",
                        description = "没有找到符合条件的高校",
                        actionText = "清除筛选",
                        onAction = {
                            searchQuery = ""
                            selectedTypeIndex = 0
                            selectedSubjectTypeIndex = 0
                        }
                    )
                }
            } else {
                items(
                    items = filteredUniversities,
                    key = { it.id }
                ) { university ->
                    UniversityListCard(
                        name = university.name,
                        location = "${university.location.city ?: ""} · ${getUniversityTypeName(university.type)}",
                        typeName = getUniversityTypeName(university.type),
                        physicsScore = university.overviewScores["2025"]?.physicsAvg,
                        historyScore = university.overviewScores["2025"]?.historyAvg,
                        onClick = { onNavigateToDetail(university.id) }
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
    placeholder: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MiuixTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
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
        }
    }
}

@Composable
private fun UniversityListCard(
    name: String,
    location: String,
    typeName: String,
    physicsScore: Int?,
    historyScore: Int?,
    onClick: () -> Unit
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
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MiuixTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = typeName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MiuixTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (physicsScore != null) {
                    ScoreColumn(label = "物理类", value = "${physicsScore}分")
                }
                if (historyScore != null) {
                    ScoreColumn(label = "历史类", value = "${historyScore}分")
                }
            }
        }
    }
}

@Composable
private fun ScoreColumn(label: String, value: String) {
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

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
import top.yukonga.miuix.kmp.basic.MiuixTheme
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TextField
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.data.UniversitiesData
import com.gaokao.model.SubjectType
import com.gaokao.model.UniversityType

@Composable
fun UniversitiesPage(onNavigateToDetail: (String) -> Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<UniversityType?>(null) }
    var selectedSubjectType by remember { mutableStateOf<SubjectType?>(null) }

    val filteredUniversities = remember(searchQuery, selectedType, selectedSubjectType) {
        UniversitiesData.universities.filter { university ->
            val matchesSearch = searchQuery.isBlank() ||
                university.name.contains(searchQuery, ignoreCase = true) ||
                university.shortName.contains(searchQuery, ignoreCase = true)
            val matchesType = selectedType == null || university.type == selectedType
            val matchesSubject = selectedSubjectType == null ||
                (selectedSubjectType == SubjectType.PHYSICS && university.overviewScores.values.any { it.physicsAvg != null }) ||
                (selectedSubjectType == SubjectType.HISTORY && university.overviewScores.values.any { it.historyAvg != null })
            matchesSearch && matchesType && matchesSubject
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = "高校列表")

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                label = "搜索高校名称",
                useLabelAsPlaceholder = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    text = "全部",
                    onClick = { selectedType = null },
                    colors = if (selectedType == null) ButtonDefaults.textButtonColorsPrimary() else ButtonDefaults.textButtonColors()
                )
                UniversityType.entries.forEach { type ->
                    TextButton(
                        text = getUniversityTypeName(type),
                        onClick = { selectedType = type },
                        colors = if (selectedType == type) ButtonDefaults.textButtonColorsPrimary() else ButtonDefaults.textButtonColors()
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    text = "全部科类",
                    onClick = { selectedSubjectType = null },
                    colors = if (selectedSubjectType == null) ButtonDefaults.textButtonColorsPrimary() else ButtonDefaults.textButtonColors()
                )
                SubjectType.entries.filter { it != SubjectType.COMPREHENSIVE }.forEach { type ->
                    TextButton(
                        text = getSubjectTypeName(type),
                        onClick = { selectedSubjectType = type },
                        colors = if (selectedSubjectType == type) ButtonDefaults.textButtonColorsPrimary() else ButtonDefaults.textButtonColors()
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredUniversities) { university ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onNavigateToDetail(university.id) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(university.name, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                                Text(
                                    "${university.location.city ?: ""} · ${getUniversityTypeName(university.type)}",
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
                                        Text("物理类", style = MiuixTheme.textStyles.paragraph)
                                        Text("${scores2025.physicsAvg}分", fontSize = 18.sp, color = MiuixTheme.colorScheme.primary)
                                    }
                                }
                                if (scores2025.historyAvg != null) {
                                    Column {
                                        Text("历史类", style = MiuixTheme.textStyles.paragraph)
                                        Text("${scores2025.historyAvg}分", fontSize = 18.sp, color = MiuixTheme.colorScheme.primary)
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

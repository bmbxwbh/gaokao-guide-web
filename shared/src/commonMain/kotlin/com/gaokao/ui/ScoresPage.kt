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
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TextField
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.data.UniversitiesData
import com.gaokao.model.MajorScore
import com.gaokao.model.SubjectType

@Composable
fun ScoresPage(onNavigateToDetail: (String, String) -> Unit = { _, _ -> }) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedSubjectType by remember { mutableStateOf(SubjectType.PHYSICS) }

    data class MajorScoreItem(
        val universityId: String,
        val universityName: String,
        val majorId: String,
        val majorName: String,
        val majorScore: MajorScore,
        val year: String
    )

    val scoreItems = remember(searchQuery, selectedSubjectType) {
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

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = "专业分数")

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                label = "搜索高校或专业名称",
                useLabelAsPlaceholder = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
            items(scoreItems) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onNavigateToDetail(item.universityId, item.majorId) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(item.universityName, style = MiuixTheme.textStyles.subtitle)
                            Text(item.year, style = MiuixTheme.textStyles.paragraph)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(item.majorName, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column {
                                Text("最低分", style = MiuixTheme.textStyles.paragraph)
                                Text("${item.majorScore.lowScore}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            }
                            Column {
                                Text("平均分", style = MiuixTheme.textStyles.paragraph)
                                Text("${item.majorScore.avgScore}", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = MiuixTheme.colorScheme.primary)
                            }
                            Column {
                                Text("最高分", style = MiuixTheme.textStyles.paragraph)
                                Text("${item.majorScore.highScore}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            }
                            Column {
                                Text("省控线", style = MiuixTheme.textStyles.paragraph)
                                Text("${item.majorScore.provinceControlLine}", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.basic.SmallTitle
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.data.UniversitiesData

@Composable
fun UniversityDetailPage(universityId: String, onNavigateToMajor: (String, String) -> Unit = { _, _ -> }) {
    val university = remember(universityId) { UniversitiesData.getUniversityById(universityId) }

    if (university == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text("未找到该高校", style = MiuixTheme.textStyles.subtitle)
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = university.name)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(university.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            TextButton(
                                text = getUniversityTypeName(university.type),
                                onClick = {},
                                colors = ButtonDefaults.textButtonColorsPrimary()
                            )
                            Text(
                                "${university.location.city ?: ""} ${university.location.district}",
                                style = MiuixTheme.textStyles.paragraph
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("建校年份：${university.foundingYear}年", style = MiuixTheme.textStyles.paragraph)
                        Text("主管部门：${university.department}", style = MiuixTheme.textStyles.paragraph)
                        Text("地址：${university.address}", style = MiuixTheme.textStyles.paragraph)
                        Text("电话：${university.phone}", style = MiuixTheme.textStyles.paragraph)
                        Text("官网：${university.website}", style = MiuixTheme.textStyles.paragraph)
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SmallTitle("学校简介")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(university.description, style = MiuixTheme.textStyles.body2)
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SmallTitle("重点学科")
                        Spacer(modifier = Modifier.height(8.dp))
                        university.keyDisciplines.forEach { discipline ->
                            Text("· $discipline", style = MiuixTheme.textStyles.body2)
                        }
                    }
                }
            }

            item {
                SmallTitle("历年分数线")
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        university.overviewScores.entries.sortedByDescending { it.key }.forEach { (year, scores) ->
                            Text(year, fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                if (scores.physicsAvg != null) {
                                    Column {
                                        Text("物理类", style = MiuixTheme.textStyles.paragraph)
                                        Text("${scores.physicsLow}-${scores.physicsHigh}", fontSize = 14.sp)
                                        Text("均分 ${scores.physicsAvg}", fontSize = 14.sp, color = MiuixTheme.colorScheme.primary)
                                    }
                                }
                                if (scores.historyAvg != null) {
                                    Column {
                                        Text("历史类", style = MiuixTheme.textStyles.paragraph)
                                        Text("${scores.historyLow}-${scores.historyHigh}", fontSize = 14.sp)
                                        Text("均分 ${scores.historyAvg}", fontSize = 14.sp, color = MiuixTheme.colorScheme.primary)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

            item {
                SmallTitle("专业列表")
            }

            items(university.majors) { major ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onNavigateToMajor(universityId, major.id) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(major.name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                                if (major.code.isNotBlank()) {
                                    Text("代码：${major.code}", style = MiuixTheme.textStyles.paragraph)
                                }
                            }
                            major.subjectRequirement?.let {
                                TextButton(
                                    text = getSubjectTypeName(it),
                                    onClick = {},
                                    colors = ButtonDefaults.textButtonColors()
                                )
                            }
                        }
                        major.scores.forEach { (year, score) ->
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("$year ${getSubjectTypeName(score.subjectType)}", style = MiuixTheme.textStyles.paragraph)
                                Text("均分 ${score.avgScore} / 最低 ${score.lowScore}", style = MiuixTheme.textStyles.paragraph)
                            }
                        }
                    }
                }
            }
        }
    }
}

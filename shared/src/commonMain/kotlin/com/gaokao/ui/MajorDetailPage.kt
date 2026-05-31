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
import com.gaokao.ui.TextButton
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.data.UniversitiesData

@Composable
fun MajorDetailPage(universityId: String, majorId: String) {
    val university = remember(universityId) { UniversitiesData.getUniversityById(universityId) }
    val major = remember(universityId, majorId) { UniversitiesData.getMajorById(universityId, majorId) }

    if (university == null || major == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text("未找到该专业", style = MiuixTheme.textStyles.subtitle)
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = major.name)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(major.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            if (major.code.isNotBlank()) {
                                Text("代码：${major.code}", style = MiuixTheme.textStyles.paragraph)
                            }
                            Text("学制：${major.duration}年", style = MiuixTheme.textStyles.paragraph)
                            Text("学位：${major.degree.name}", style = MiuixTheme.textStyles.paragraph)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("所属院校：${university.name}", style = MiuixTheme.textStyles.paragraph)
                        if (major.department.isNotBlank()) {
                            Text("所属院系：${major.department}", style = MiuixTheme.textStyles.paragraph)
                        }
                        major.subjectRequirement?.let {
                            Text("选科要求：${getSubjectTypeName(it)}", style = MiuixTheme.textStyles.paragraph)
                        }
                        if (!major.tags.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                major.tags.forEach { tag ->
                                    TextButton(
                                        text = tag,
                                        onClick = {},
                                        colors = ButtonDefaults.textButtonColors()
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SmallTitle("专业介绍")
                        Spacer(modifier = Modifier.height(8.dp))
                        if (major.introduction.isNotBlank()) {
                            Text(major.introduction, style = MiuixTheme.textStyles.body2)
                        } else {
                            Text("暂无介绍", style = MiuixTheme.textStyles.body2, color = MiuixTheme.colorScheme.onSurfaceVariantSummary)
                        }
                    }
                }
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SmallTitle("培养目标")
                        Spacer(modifier = Modifier.height(8.dp))
                        if (major.trainingGoal.isNotBlank()) {
                            Text(major.trainingGoal, style = MiuixTheme.textStyles.body2)
                        } else {
                            Text("暂无信息", style = MiuixTheme.textStyles.body2, color = MiuixTheme.colorScheme.onSurfaceVariantSummary)
                        }
                    }
                }
            }

            if (major.mainCourses.isNotEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            SmallTitle("主要课程")
                            Spacer(modifier = Modifier.height(8.dp))
                            major.mainCourses.forEach { course ->
                                Text("· $course", style = MiuixTheme.textStyles.body2)
                            }
                        }
                    }
                }
            }

            if (major.employmentDirections.isNotEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            SmallTitle("就业方向")
                            Spacer(modifier = Modifier.height(8.dp))
                            major.employmentDirections.forEach { direction ->
                                Text("· $direction", style = MiuixTheme.textStyles.body2)
                            }
                        }
                    }
                }
            }

            if (major.furtherStudyDirections.isNotEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            SmallTitle("深造方向")
                            Spacer(modifier = Modifier.height(8.dp))
                            major.furtherStudyDirections.forEach { direction ->
                                Text("· $direction", style = MiuixTheme.textStyles.body2)
                            }
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
                        major.scores.entries.sortedByDescending { it.key }.forEach { (year, score) ->
                            Text(year, fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column {
                                    Text("批次", style = MiuixTheme.textStyles.paragraph)
                                    Text(getBatchName(score.batch), fontSize = 14.sp)
                                }
                                Column {
                                    Text("科类", style = MiuixTheme.textStyles.paragraph)
                                    Text(getSubjectTypeName(score.subjectType), fontSize = 14.sp)
                                }
                                Column {
                                    Text("最低分", style = MiuixTheme.textStyles.paragraph)
                                    Text("${score.lowScore}", fontSize = 14.sp)
                                }
                                Column {
                                    Text("平均分", style = MiuixTheme.textStyles.paragraph)
                                    Text("${score.avgScore}", fontSize = 14.sp, color = MiuixTheme.colorScheme.primary)
                                }
                                Column {
                                    Text("最高分", style = MiuixTheme.textStyles.paragraph)
                                    Text("${score.highScore}", fontSize = 14.sp)
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column {
                                    Text("省控线", style = MiuixTheme.textStyles.paragraph)
                                    Text("${score.provinceControlLine}", fontSize = 14.sp)
                                }
                                score.planCount?.let {
                                    Column {
                                        Text("计划数", style = MiuixTheme.textStyles.paragraph)
                                        Text("$it", fontSize = 14.sp)
                                    }
                                }
                                score.actualCount?.let {
                                    Column {
                                        Text("实际数", style = MiuixTheme.textStyles.paragraph)
                                        Text("$it", fontSize = 14.sp)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

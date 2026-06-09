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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import com.gaokao.ui.components.FilterChipGroup
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.basic.SmallTitle
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.data.UniversitiesData
import com.gaokao.model.SubjectType

@Composable
fun UniversityDetailPage(
    universityId: String,
    onNavigateToMajor: (String, String) -> Unit = { _, _ -> }
) {
    val university = remember(universityId) { UniversitiesData.getUniversityById(universityId) }

    if (university == null) {
        EmptyState(
            title = "未找到该高校",
            description = "抱歉，未能找到您查询的高校信息",
            modifier = Modifier.fillMaxSize()
        )
        return
    }

    var selectedSubjectTypeIndex by remember { mutableStateOf(0) }
    val subjectTypes = listOf("全部", "物理类", "历史类")

    val filteredMajors by remember(selectedSubjectTypeIndex, university.majors) {
        derivedStateOf {
            when (selectedSubjectTypeIndex) {
                0 -> university.majors
                1 -> university.majors.filter { it.subjectRequirement == SubjectType.PHYSICS }
                2 -> university.majors.filter { it.subjectRequirement == SubjectType.HISTORY }
                else -> university.majors
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = university.name)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                UniversityHeaderCard(
                    name = university.name,
                    type = getUniversityTypeName(university.type),
                    location = "${university.location.city ?: ""} ${university.location.district}",
                    foundingYear = university.foundingYear,
                    department = university.department,
                    address = university.address,
                    phone = university.phone,
                    website = university.website
                )
            }

            item {
                UniversityInfoCard(
                    description = university.description,
                    keyDisciplines = university.keyDisciplines
                )
            }

            item {
                ScoreOverviewCard(scores = university.overviewScores)
            }

            item {
                FilterChipGroup(
                    options = subjectTypes,
                    selectedIndex = selectedSubjectTypeIndex,
                    onSelect = { selectedSubjectTypeIndex = it }
                )
            }

            if (filteredMajors.isEmpty()) {
                item {
                    EmptyState(
                        title = "暂无专业",
                        description = "该类别下暂无专业信息",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                items(
                    items = filteredMajors,
                    key = { it.id }
                ) { major ->
                    MajorCard(
                        majorName = major.name,
                        majorCode = major.code,
                        subjectType = major.subjectRequirement?.let { getSubjectTypeName(it) },
                        scores = major.scores,
                        onClick = { onNavigateToMajor(universityId, major.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun UniversityHeaderCard(
    name: String,
    type: String,
    location: String,
    foundingYear: Int,
    department: String,
    address: String,
    phone: String,
    website: String
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MiuixTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🎓",
                        fontSize = 28.sp
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                Column {
                    Text(
                        text = name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(MiuixTheme.colorScheme.primaryContainer)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = type,
                                fontSize = 12.sp,
                                color = MiuixTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Text(
                            text = location,
                            style = MiuixTheme.textStyles.paragraph
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            InfoRow(icon = "🏛️", label = "建校年份", value = "${foundingYear}年")
            InfoRow(icon = "📋", label = "主管部门", value = department)
            InfoRow(icon = "📍", label = "地址", value = address)
            InfoRow(icon = "📞", label = "电话", value = phone)
            InfoRow(icon = "🌐", label = "官网", value = website)
        }
    }
}

@Composable
private fun InfoRow(icon: String, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = icon, fontSize = 16.sp)
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        Text(
            text = "$label：",
            style = MiuixTheme.textStyles.paragraph,
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary
        )
        Text(
            text = value,
            style = MiuixTheme.textStyles.paragraph
        )
    }
}

@Composable
private fun UniversityInfoCard(
    description: String,
    keyDisciplines: List<String>
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            SmallTitle("学校简介")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MiuixTheme.textStyles.body2,
                lineHeight = 22.sp
            )

            if (keyDisciplines.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                SmallTitle("重点学科")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    keyDisciplines.take(5).forEach { discipline ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MiuixTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = discipline,
                                fontSize = 12.sp,
                                color = MiuixTheme.colorScheme.onSurfaceVariantSummary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScoreOverviewCard(scores: Map<String, com.gaokao.model.YearOverviewScore>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            SmallTitle("历年分数线")
            Spacer(modifier = Modifier.height(12.dp))

            scores.entries.sortedByDescending { it.key }.forEach { (year, yearScores) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = year,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        if (yearScores.physicsAvg != null) {
                            ScoreChip(
                                label = "物理",
                                low = yearScores.physicsLow!!,
                                avg = yearScores.physicsAvg!!,
                                high = yearScores.physicsHigh!!
                            )
                        }
                        if (yearScores.historyAvg != null) {
                            ScoreChip(
                                label = "历史",
                                low = yearScores.historyLow!!,
                                avg = yearScores.historyAvg!!,
                                high = yearScores.historyHigh!!
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScoreChip(label: String, low: Int, avg: Int, high: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = MiuixTheme.colorScheme.onSurfaceVariantSummary
        )
        Text(
            text = "${low}-${high}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "均分 $avg",
            fontSize = 12.sp,
            color = MiuixTheme.colorScheme.primary
        )
    }
}

@Composable
private fun MajorCard(
    majorName: String,
    majorCode: String,
    subjectType: String?,
    scores: Map<String, com.gaokao.model.MajorScore>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = majorName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    if (majorCode.isNotBlank()) {
                        Text(
                            text = "代码：$majorCode",
                            style = MiuixTheme.textStyles.paragraph
                        )
                    }
                }
                if (subjectType != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MiuixTheme.colorScheme.secondaryContainer)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = subjectType,
                            fontSize = 12.sp,
                            color = MiuixTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            scores.entries.take(2).forEach { (year, score) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(MiuixTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = year.takeLast(2),
                                fontSize = 10.sp,
                                color = MiuixTheme.colorScheme.onSurfaceVariantSummary
                            )
                        }
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(
                            text = getSubjectTypeName(score.subjectType),
                            style = MiuixTheme.textStyles.body2
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "最低 ${score.lowScore}",
                            fontSize = 12.sp,
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary
                        )
                        Text(
                            text = "均分 ${score.avgScore}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MiuixTheme.colorScheme.primary
                        )
                        Text(
                            text = "最高 ${score.highScore}",
                            fontSize = 12.sp,
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary
                        )
                    }
                }
            }
        }
    }
}

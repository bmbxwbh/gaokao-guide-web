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
import com.gaokao.ui.TextField
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.data.UniversitiesData
import com.gaokao.model.AdmissionProbability
import com.gaokao.model.RecommendationResult
import com.gaokao.model.SubjectType

@Composable
fun RecommendationPage() {
    var totalScoreText by remember { mutableStateOf("") }
    var selectedSubjectType by remember { mutableStateOf(SubjectType.PHYSICS) }
    var recommendations by remember { mutableStateOf<List<RecommendationResult>>(emptyList()) }
    var hasSearched by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = "智能推荐")

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SmallTitle("输入分数")

                        Spacer(modifier = Modifier.height(12.dp))

                        TextField(
                            value = totalScoreText,
                            onValueChange = { totalScoreText = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = "总分",
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

                        Spacer(modifier = Modifier.height(16.dp))

                        TextButton(
                            text = "生成推荐",
                            onClick = {
                                val score = totalScoreText.toIntOrNull()
                                if (score != null) {
                                    hasSearched = true
                                    recommendations = generateRecommendations(score, selectedSubjectType)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.textButtonColorsPrimary()
                        )
                    }
                }
            }

            if (hasSearched && recommendations.isEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("暂无推荐结果", style = MiuixTheme.textStyles.subtitle)
                        }
                    }
                }
            }

            items(recommendations) { result ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(result.universityName, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                                Text(result.majorName, style = MiuixTheme.textStyles.paragraph)
                            }
                            TextButton(
                                text = getProbabilityText(result.probability),
                                onClick = {},
                                colors = when (result.probability) {
                                    AdmissionProbability.SAFE, AdmissionProbability.SURE -> ButtonDefaults.textButtonColorsPrimary()
                                    AdmissionProbability.STABLE -> ButtonDefaults.textButtonColorsPrimary()
                                    AdmissionProbability.STRETCH -> ButtonDefaults.textButtonColors()
                                    AdmissionProbability.RISKY -> ButtonDefaults.textButtonColors()
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column {
                                Text("匹配度", style = MiuixTheme.textStyles.paragraph)
                                Text("${result.matchScore}%", fontSize = 16.sp, color = MiuixTheme.colorScheme.primary)
                            }
                            Column {
                                Text("平均分", style = MiuixTheme.textStyles.paragraph)
                                Text("${result.avgScore}", fontSize = 16.sp)
                            }
                            Column {
                                Text("最低分", style = MiuixTheme.textStyles.paragraph)
                                Text("${result.lowScore}", fontSize = 16.sp)
                            }
                        }
                        if (result.aiSuggestion != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(result.aiSuggestion!!, style = MiuixTheme.textStyles.body2, color = MiuixTheme.colorScheme.onSurfaceVariantSummary)
                        }
                    }
                }
            }
        }
    }
}

private fun generateRecommendations(totalScore: Int, subjectType: SubjectType): List<RecommendationResult> {
    val results = mutableListOf<RecommendationResult>()
    UniversitiesData.universities.forEach { university ->
        university.majors.forEach { major ->
            major.scores.forEach { (year, score) ->
                if (score.subjectType == subjectType) {
                    val diff = totalScore - score.avgScore
                    val probability = when {
                        diff >= 20 -> AdmissionProbability.SAFE
                        diff >= 10 -> AdmissionProbability.SURE
                        diff >= 0 -> AdmissionProbability.STABLE
                        diff >= -10 -> AdmissionProbability.STRETCH
                        else -> AdmissionProbability.RISKY
                    }
                    val matchScore = when {
                        diff >= 20 -> 95
                        diff >= 10 -> 85
                        diff >= 0 -> 75
                        diff >= -10 -> 60
                        else -> 40
                    }
                    if (diff >= -30) {
                        results.add(
                            RecommendationResult(
                                universityId = university.id,
                                universityName = university.name,
                                universityShortName = university.shortName,
                                majorId = major.id,
                                majorName = major.name,
                                majorCode = major.code,
                                year = year,
                                probability = probability,
                                matchScore = matchScore,
                                avgScore = score.avgScore,
                                lowScore = score.lowScore,
                                subjectType = subjectType
                            )
                        )
                    }
                }
            }
        }
    }
    return results.sortedByDescending { it.matchScore }
}

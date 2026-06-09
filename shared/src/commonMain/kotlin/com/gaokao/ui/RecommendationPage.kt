package com.gaokao.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gaokao.data.UniversitiesData
import com.gaokao.model.AdmissionProbability
import com.gaokao.model.RecommendationResult
import com.gaokao.model.SubjectType
import com.gaokao.ui.components.EmptyState
import com.gaokao.ui.components.FilterChipGroup
import com.gaokao.ui.components.RecommendationCard
import com.gaokao.ui.components.SkeletonCard
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun RecommendationPage() {
    var totalScoreText by remember { mutableStateOf("") }
    var selectedSubjectType by remember { mutableStateOf(SubjectType.PHYSICS) }
    var recommendations by remember { mutableStateOf<List<RecommendationResult>>(emptyList()) }
    var hasSearched by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val recommendations by remember(totalScoreText, selectedSubjectType, hasSearched) {
        derivedStateOf {
            if (!hasSearched) return@derivedStateOf emptyList()
            val score = totalScoreText.toIntOrNull() ?: return@derivedStateOf emptyList()
            generateEnhancedRecommendations(score, selectedSubjectType)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = "智能推荐")

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                InputCard(
                    scoreText = totalScoreText,
                    onScoreChange = { totalScoreText = it },
                    selectedSubjectType = selectedSubjectType,
                    onSubjectTypeChange = { selectedSubjectType = it },
                    onSearch = {
                        if (totalScoreText.isNotBlank()) {
                            isLoading = true
                            hasSearched = true
                        }
                    }
                )
            }

            if (hasSearched && totalScoreText.isBlank()) {
                item {
                    EmptyState(
                        title = "请输入分数",
                        description = "请输入您的高考成绩，系统将为您推荐合适的高校和专业"
                    )
                }
            } else if (hasSearched && isLoading) {
                items(3) {
                    SkeletonCard()
                }
            } else if (hasSearched && recommendations.isEmpty()) {
                item {
                    EmptyState(
                        title = "暂无推荐结果",
                        description = "没有找到与您的分数匹配的高校专业",
                        actionText = "调整分数重试",
                        onAction = { totalScoreText = "" }
                    )
                }
            } else if (recommendations.isNotEmpty()) {
                item {
                    ResultSummary(
                        score = totalScoreText.toIntOrNull() ?: 0,
                        count = recommendations.size
                    )
                }

                items(
                    items = recommendations.take(20),
                    key = { "${it.universityId}-${it.majorId}-${it.year}" }
                ) { result ->
                    RecommendationCard(
                        universityName = result.universityName,
                        majorName = result.majorName,
                        probability = result.probability,
                        matchScore = result.matchScore,
                        avgScore = result.avgScore,
                        lowScore = result.lowScore,
                        aiSuggestion = result.aiSuggestion
                    )
                }

                if (recommendations.size > 20) {
                    item {
                        Text(
                            text = "显示前 20 条推荐结果，向上滚动查看更多",
                            style = MiuixTheme.textStyles.paragraph,
                            color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InputCard(
    scoreText: String,
    onScoreChange: (String) -> Unit,
    selectedSubjectType: SubjectType,
    onSubjectTypeChange: (SubjectType) -> Unit,
    onSearch: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            SmallTitle("输入分数")

            Spacer(modifier = Modifier.height(12.dp))

            ScoreInput(
                value = scoreText,
                onValueChange = onScoreChange,
                onDone = onSearch
            )

            Spacer(modifier = Modifier.height(12.dp))

            FilterChipGroup(
                options = listOf("物理类", "历史类"),
                selectedIndex = if (selectedSubjectType == SubjectType.PHYSICS) 0 else 1,
                onSelect = { index ->
                    onSubjectTypeChange(if (index == 0) SubjectType.PHYSICS else SubjectType.HISTORY)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                text = "生成推荐",
                onClick = onSearch,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColorsPrimary()
            )
        }
    }
}

@Composable
private fun ScoreInput(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MiuixTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "🎯",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Column {
                    Text(
                        text = "高考总分",
                        style = MiuixTheme.textStyles.paragraph,
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = "请输入分数",
                                style = MiuixTheme.textStyles.title2,
                                color = MiuixTheme.colorScheme.onSurfaceVariantSummary
                            )
                        }
                        BasicTextField(
                            value = value,
                            onValueChange = { newValue ->
                                if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                    val numValue = newValue.toIntOrNull()
                                    if (numValue == null || numValue <= 750) {
                                        onValueChange(newValue)
                                    }
                                }
                            },
                            textStyle = MiuixTheme.textStyles.title2.copy(
                                color = MiuixTheme.colorScheme.onSurface
                            ),
                            cursorBrush = SolidColor(MiuixTheme.colorScheme.primary),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { onDone() }
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            Text(
                text = "分",
                style = MiuixTheme.textStyles.title3,
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary
            )
        }
    }
}

@Composable
private fun ResultSummary(
    score: Int,
    count: Int
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$score",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MiuixTheme.colorScheme.primary
                )
                Text(
                    text = "您的分数",
                    style = MiuixTheme.textStyles.paragraph
                )
            }
            Box(
                modifier = Modifier
                    .size(1.dp, 40.dp)
                    .background(MiuixTheme.colorScheme.outlineVariant)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$count",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MiuixTheme.colorScheme.primary
                )
                Text(
                    text = "推荐专业",
                    style = MiuixTheme.textStyles.paragraph
                )
            }
        }
    }
}

private fun generateEnhancedRecommendations(
    totalScore: Int,
    subjectType: SubjectType
): List<RecommendationResult> {
    val results = mutableListOf<RecommendationResult>()
    val provinceControlLine = if (subjectType == SubjectType.PHYSICS) 518 else 533

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

                    val aiSuggestion = when (probability) {
                        AdmissionProbability.SAFE, AdmissionProbability.SURE -> {
                            "您的分数高于平均分 ${if (diff >= 20) "20" else "10"} 分以上，录取把握较大"
                        }
                        AdmissionProbability.STABLE -> {
                            "您的分数接近平均分，录取概率较高，建议作为主要志愿填报"
                        }
                        AdmissionProbability.STRETCH -> {
                            "您的分数略低于平均分，存在一定风险，建议谨慎填报"
                        }
                        AdmissionProbability.RISKY -> {
                            "您的分数低于平均分较多，录取风险较大，建议作为保底志愿"
                        }
                    }

                    if (diff >= -50) {
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
                                subjectType = subjectType,
                                aiSuggestion = aiSuggestion
                            )
                        )
                    }
                }
            }
        }
    }

    return results.sortedWith(
        compareByDescending<RecommendationResult> { it.matchScore }
            .thenBy { it.avgScore }
            .thenByDescending { it.probability.ordinal }
    )
}

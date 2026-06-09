package com.gaokao

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gaokao.ui.AppState
import com.gaokao.ui.ComparisonPage
import com.gaokao.ui.FavoritesPage
import com.gaokao.ui.GaokaoGuideTheme
import com.gaokao.ui.HomePage
import com.gaokao.ui.MajorDetailPage
import com.gaokao.ui.RecommendationPage
import com.gaokao.ui.Screen
import com.gaokao.ui.ScoresPage
import com.gaokao.ui.UniversitiesPage
import com.gaokao.ui.UniversityDetailPage
import top.yukonga.miuix.kmp.basic.HorizontalDivider
import top.yukonga.miuix.kmp.basic.Scaffold
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun GaokaoGuideApp(
    padding: PaddingValues = PaddingValues()
) {
    val appState = remember { AppState() }

    GaokaoGuideTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    currentScreen = appState.currentScreen,
                    onNavigate = { screen -> appState.currentScreen = screen }
                )
            }
        ) { paddingValues ->
            AnimatedContent(
                targetState = appState.currentScreen,
                transitionSpec = {
                    fadeIn(animationSpec = tween(200)) togetherWith fadeOut(animationSpec = tween(200))
                },
                label = "screenTransition"
            ) { screen ->
                Box(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    when (screen) {
                        is Screen.Home -> HomePage(
                            onNavigateToUniversities = { appState.currentScreen = Screen.Universities },
                            onNavigateToScores = { appState.currentScreen = Screen.Scores },
                            onNavigateToRecommendation = { appState.currentScreen = Screen.Recommendation },
                            onNavigateToFavorites = { appState.currentScreen = Screen.Favorites },
                            onNavigateToComparison = { appState.currentScreen = Screen.Comparison }
                        )
                        is Screen.Universities -> UniversitiesPage(
                            onNavigateToDetail = { id -> appState.currentScreen = Screen.UniversityDetail(id) }
                        )
                        is Screen.Scores -> ScoresPage(
                            onNavigateToDetail = { uniId, majorId -> appState.currentScreen = Screen.MajorDetail(uniId, majorId) }
                        )
                        is Screen.Recommendation -> RecommendationPage()
                        is Screen.Favorites -> FavoritesPage(
                            onNavigateToDetail = { id -> appState.currentScreen = Screen.UniversityDetail(id) }
                        )
                        is Screen.Comparison -> ComparisonPage(
                            onNavigateToDetail = { id -> appState.currentScreen = Screen.UniversityDetail(id) }
                        )
                        is Screen.UniversityDetail -> UniversityDetailPage(
                            universityId = screen.universityId,
                            onNavigateToMajor = { uniId, majorId -> appState.currentScreen = Screen.MajorDetail(uniId, majorId) }
                        )
                        is Screen.MajorDetail -> MajorDetailPage(
                            universityId = screen.universityId,
                            majorId = screen.majorId
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit
) {
    Column {
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth().height(64.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = "🏠",
                label = "首页",
                selected = currentScreen is Screen.Home,
                onClick = { onNavigate(Screen.Home) }
            )
            BottomNavItem(
                icon = "🎓",
                label = "高校",
                selected = currentScreen is Screen.Universities || currentScreen is Screen.UniversityDetail,
                onClick = { onNavigate(Screen.Universities) }
            )
            BottomNavItem(
                icon = "📊",
                label = "分数",
                selected = currentScreen is Screen.Scores || currentScreen is Screen.MajorDetail,
                onClick = { onNavigate(Screen.Scores) }
            )
            BottomNavItem(
                icon = "✨",
                label = "推荐",
                selected = currentScreen is Screen.Recommendation,
                onClick = { onNavigate(Screen.Recommendation) }
            )
            BottomNavItem(
                icon = "❤️",
                label = "我的",
                selected = currentScreen is Screen.Favorites || currentScreen is Screen.Comparison,
                onClick = { onNavigate(Screen.Favorites) }
            )
        }
    }
}

@Composable
private fun RowScope.BottomNavItem(
    icon: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(
                    if (selected) MiuixTheme.colorScheme.primaryContainer
                    else MiuixTheme.colorScheme.surface
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                fontSize = 16.sp
            )
        }
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.onSurfaceVariantSummary,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

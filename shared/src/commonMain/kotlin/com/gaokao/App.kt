package com.gaokao

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun GaokaoGuideApp(
    padding: PaddingValues = PaddingValues()
) {
    val appState = remember { AppState() }

    GaokaoGuideTheme {
        Scaffold(
            bottomBar = {
                Column {
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BottomNavItem(
                            label = "首页",
                            selected = appState.currentScreen is Screen.Home
                        ) { appState.currentScreen = Screen.Home }
                        BottomNavItem(
                            label = "高校",
                            selected = appState.currentScreen is Screen.Universities
                        ) { appState.currentScreen = Screen.Universities }
                        BottomNavItem(
                            label = "分数",
                            selected = appState.currentScreen is Screen.Scores
                        ) { appState.currentScreen = Screen.Scores }
                        BottomNavItem(
                            label = "推荐",
                            selected = appState.currentScreen is Screen.Recommendation
                        ) { appState.currentScreen = Screen.Recommendation }
                        BottomNavItem(
                            label = "我的",
                            selected = appState.currentScreen is Screen.Favorites || appState.currentScreen is Screen.Comparison
                        ) { appState.currentScreen = Screen.Favorites }
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                when (appState.currentScreen) {
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
                        universityId = (appState.currentScreen as Screen.UniversityDetail).universityId,
                        onNavigateToMajor = { uniId, majorId -> appState.currentScreen = Screen.MajorDetail(uniId, majorId) }
                    )
                    is Screen.MajorDetail -> MajorDetailPage(
                        universityId = (appState.currentScreen as Screen.MajorDetail).universityId,
                        majorId = (appState.currentScreen as Screen.MajorDetail).majorId
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.BottomNavItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.weight(1f).fillMaxHeight().clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.onSurfaceVariantSummary
        )
    }
}

package com.gaokao.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.gaokao.ui.components.EmptyState
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.TopAppBar
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.theme.MiuixTheme
import com.gaokao.data.UniversitiesData
import com.gaokao.model.Favorite

@Composable
fun FavoritesPage(onNavigateToDetail: (String) -> Unit = {}) {
    var favorites by remember {
        mutableStateOf(
            listOf(
                Favorite(id = "fav-1", type = "university", targetId = "xihua-university", universityId = "xihua-university", timestamp = 0L),
                Favorite(id = "fav-2", type = "university", targetId = "chengdu-university", universityId = "chengdu-university", timestamp = 0L)
            )
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = "我的收藏")

        if (favorites.isEmpty()) {
            EmptyState(
                title = "暂无收藏",
                description = "您还没有收藏任何高校，浏览高校列表并添加收藏",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favorites, key = { it.id }) { favorite ->
                    val university = UniversitiesData.getUniversityById(favorite.targetId)
                    if (university != null) {
                        FavoriteCard(
                            name = university.name,
                            location = "${university.location.city ?: ""} · ${getUniversityTypeName(university.type)}",
                            physicsScore = university.overviewScores["2025"]?.physicsAvg,
                            historyScore = university.overviewScores["2025"]?.historyAvg,
                            onClick = { onNavigateToDetail(university.id) },
                            onRemove = { favorites = favorites.filterNot { it.id == favorite.id } }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteCard(
    name: String,
    location: String,
    physicsScore: Int?,
    historyScore: Int?,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        insideMargin = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.Center
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
                if (physicsScore != null || historyScore != null) {
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        physicsScore?.let {
                            Text(
                                text = "物理 $it",
                                fontSize = 14.sp,
                                color = MiuixTheme.colorScheme.primary
                            )
                        }
                        historyScore?.let {
                            Text(
                                text = "历史 $it",
                                fontSize = 14.sp,
                                color = MiuixTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
            TextButton(
                text = "取消",
                onClick = onRemove
            )
        }
    }
}

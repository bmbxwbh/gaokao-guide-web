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
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.theme.MiuixTheme
import com.gaokao.ui.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.basic.TopAppBar
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
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "暂无收藏",
                    modifier = Modifier.fillMaxWidth(),
                    style = MiuixTheme.textStyles.subtitle,
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favorites, key = { it.id }) { favorite ->
                    val university = UniversitiesData.getUniversityById(favorite.targetId)
                    if (university != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onNavigateToDetail(university.id) }
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(university.name, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                                    Text(
                                        "${university.location.city ?: ""} · ${getUniversityTypeName(university.type)}",
                                        style = MiuixTheme.textStyles.paragraph
                                    )
                                }
                                TextButton(
                                    text = "取消收藏",
                                    onClick = {
                                        favorites = favorites.filterNot { it.id == favorite.id }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

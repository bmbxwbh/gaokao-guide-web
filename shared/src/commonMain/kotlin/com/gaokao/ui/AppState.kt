package com.gaokao.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.gaokao.model.ComparisonItem
import com.gaokao.model.Favorite
import com.gaokao.model.RecommendationResult
import com.gaokao.model.SubjectType
import com.gaokao.model.University
import com.gaokao.model.UserScores

class AppState {
    var currentScreen by mutableStateOf<Screen>(Screen.Home)
    var selectedUniversity by mutableStateOf<University?>(null)
    var searchQuery by mutableStateOf("")
    var selectedSubjectType by mutableStateOf<SubjectType?>(null)
    var userScores by mutableStateOf<UserScores?>(null)
    var favorites by mutableStateOf<List<Favorite>>(emptyList())
    var comparisonList by mutableStateOf<List<ComparisonItem>>(emptyList())
    var recommendations by mutableStateOf<List<RecommendationResult>>(emptyList())

    // 筛选状态
    var filterState by mutableStateOf(FilterState())
        private set

    // 搜索历史
    var searchHistory by mutableStateOf<List<String>>(emptyList())
        private set

    fun updateFilterState(
        searchQuery: String = filterState.searchQuery,
        year: String = filterState.selectedYear,
        batch: Int? = filterState.selectedBatch,
        universityType: Int? = filterState.selectedUniversityType
    ) {
        filterState = FilterState(
            searchQuery = searchQuery,
            selectedYear = year,
            selectedBatch = batch,
            selectedUniversityType = universityType
        )
    }

    fun addToSearchHistory(query: String) {
        if (query.isBlank()) return
        searchHistory = (listOf(query) + searchHistory.filter { it != query }).take(10)
    }

    fun clearSearchHistory() {
        searchHistory = emptyList()
    }

    // 收藏操作
    fun addToFavorites(favorite: Favorite) {
        if (favorites.none { it.id == favorite.id }) {
            favorites = favorites + favorite
        }
    }

    fun removeFromFavorites(favoriteId: String) {
        favorites = favorites.filter { it.id != favoriteId }
    }

    fun isFavorite(targetId: String): Boolean {
        return favorites.any { it.targetId == targetId }
    }

    // 对比操作
    fun addToComparison(item: ComparisonItem) {
        if (comparisonList.size < 4 && comparisonList.none { it.id == item.id }) {
            comparisonList = comparisonList + item
        }
    }

    fun removeFromComparison(itemId: String) {
        comparisonList = comparisonList.filter { it.id != itemId }
    }

    fun isInComparison(targetId: String): Boolean {
        return comparisonList.any { it.targetId == targetId }
    }

    fun clearComparison() {
        comparisonList = emptyList()
    }
}

sealed class Screen {
    data object Home : Screen()
    data object Universities : Screen()
    data object Scores : Screen()
    data object Recommendation : Screen()
    data object Favorites : Screen()
    data object Comparison : Screen()
    data class UniversityDetail(val universityId: String) : Screen()
    data class MajorDetail(val universityId: String, val majorId: String) : Screen()
}

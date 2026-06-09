package com.gaokao.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.gaokao.model.BatchType
import com.gaokao.model.Favorite
import com.gaokao.model.SubjectType
import com.gaokao.model.UniversityType

/**
 * 应用状态管理器
 * 集中管理所有应用状态
 */
@Stable
class AppStateManager {
    // 导航状态
    private val _currentScreen = mutableStateOf<Screen>(Screen.Home)
    val currentScreen: State<Screen> get() = _currentScreen

    // 搜索和筛选状态
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> get() = _searchQuery

    private val _selectedUniversityType = mutableStateOf<UniversityType?>(null)
    val selectedUniversityType: State<UniversityType?> get() = _selectedUniversityType

    private val _selectedSubjectType = mutableStateOf<SubjectType?>(null)
    val selectedSubjectType: State<SubjectType?> get() = _selectedSubjectType

    private val _selectedBatch = mutableStateOf<BatchType?>(null)
    val selectedBatch: State<BatchType?> get() = _selectedBatch

    private val _selectedYear = mutableStateOf("2025")
    val selectedYear: State<String> get() = _selectedYear

    // 用户数据状态
    private val _userScore = mutableStateOf<Int?>(null)
    val userScore: State<Int?> get() = _userScore

    private val _favorites = mutableStateOf<List<Favorite>>(emptyList())
    val favorites: State<List<Favorite>> get() = _favorites

    // 搜索历史
    private val _searchHistory = mutableStateOf<List<String>>(emptyList())
    val searchHistory: State<List<String>> get() = _searchHistory

    // 加载状态
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    // 导航方法
    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun goBack() {
        _currentScreen.value = when (val current = _currentScreen.value) {
            is Screen.UniversityDetail -> Screen.Universities
            is Screen.MajorDetail -> {
                val uniId = current.universityId
                Screen.UniversityDetail(uniId)
            }
            else -> Screen.Home
        }
    }

    // 筛选方法
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isNotBlank() && !_searchHistory.value.contains(query)) {
            _searchHistory.value = (listOf(query) + _searchHistory.value).take(10)
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }

    fun updateUniversityType(type: UniversityType?) {
        _selectedUniversityType.value = type
    }

    fun updateSubjectType(type: SubjectType?) {
        _selectedSubjectType.value = type
    }

    fun updateBatch(batch: BatchType?) {
        _selectedBatch.value = batch
    }

    fun updateYear(year: String) {
        _selectedYear.value = year
    }

    fun clearFilters() {
        _searchQuery.value = ""
        _selectedUniversityType.value = null
        _selectedSubjectType.value = null
        _selectedBatch.value = null
    }

    // 用户数据方法
    fun updateUserScore(score: Int?) {
        _userScore.value = score
    }

    // 收藏方法
    fun addFavorite(favorite: Favorite) {
        if (_favorites.value.none { it.id == favorite.id }) {
            _favorites.value = _favorites.value + favorite
        }
    }

    fun removeFavorite(favoriteId: String) {
        _favorites.value = _favorites.value.filter { it.id != favoriteId }
    }

    fun isFavorite(targetId: String): Boolean {
        return _favorites.value.any { it.targetId == targetId }
    }

    fun clearFavorites() {
        _favorites.value = emptyList()
    }

    // 搜索历史方法
    fun clearSearchHistory() {
        _searchHistory.value = emptyList()
    }

    fun removeFromHistory(query: String) {
        _searchHistory.value = _searchHistory.value.filter { it != query }
    }

    // 加载状态方法
    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setError(message: String?) {
        _errorMessage.value = message
    }

    fun clearError() {
        _errorMessage.value = null
    }

    // 重置所有状态
    fun resetAll() {
        clearFilters()
        clearFavorites()
        clearSearchHistory()
        clearError()
        _userScore.value = null
    }
}

/**
 * Remember AppStateManager
 */
@Composable
fun rememberAppStateManager(): AppStateManager {
    return remember { AppStateManager() }
}

/**
 * 派生状态工具
 */
@Composable
fun <T> rememberDerivedStateOf(
    key1: Any?,
    calculation: () -> T
): State<T> {
    return remember(key1) { derivedStateOf(calculation) }
}

@Composable
fun <T> rememberDerivedStateOf(
    key1: Any?,
    key2: Any?,
    calculation: () -> T
): State<T> {
    return remember(key1, key2) { derivedStateOf(calculation) }
}

@Composable
fun <T> rememberDerivedStateOf(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    calculation: () -> T
): State<T> {
    return remember(key1, key2, key3) { derivedStateOf(calculation) }
}

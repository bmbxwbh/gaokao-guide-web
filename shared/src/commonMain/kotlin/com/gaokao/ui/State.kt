package com.gaokao.ui

/**
 * 数据加载状态
 */
sealed class LoadingState<out T> {
    data object Loading : LoadingState<Nothing>()
    data class Success<T>(val data: T) : LoadingState<T>()
    data class Error(val message: String) : LoadingState<Nothing>()
}

/**
 * 筛选状态
 */
data class FilterState(
    val searchQuery: String = "",
    val selectedYear: String = "2025",
    val selectedBatch: Int? = null,
    val selectedUniversityType: Int? = null
)

/**
 * 分页数据
 */
data class PagedData<T>(
    val items: List<T>,
    val currentPage: Int,
    val pageSize: Int,
    val hasMore: Boolean
) {
    companion object {
        fun <T> empty() = PagedData(
            items = emptyList(),
            currentPage = 0,
            pageSize = 20,
            hasMore = false
        )
    }
}

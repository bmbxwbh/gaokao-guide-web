package com.gaokao.data

import com.gaokao.model.Major
import com.gaokao.model.RecommendationResult
import com.gaokao.model.SubjectType
import com.gaokao.model.University
import com.gaokao.model.AdmissionProbability
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 数据仓库
 * 统一管理数据获取和缓存
 */
object DataRepository {

    // 内存缓存
    private val universityCache = mutableMapOf<String, University>()
    private val majorCache = mutableMapOf<String, Major>()

    /**
     * 获取所有高校
     */
    fun getAllUniversities(): List<University> {
        return UniversitiesData.universities
    }

    /**
     * 根据ID获取高校
     */
    fun getUniversityById(id: String): University? {
        return universityCache.getOrPut(id) {
            UniversitiesData.getUniversityById(id)
        } ?: UniversitiesData.getUniversityById(id)
    }

    /**
     * 根据ID获取专业
     */
    fun getMajorById(universityId: String, majorId: String): Major? {
        val key = "$universityId-$majorId"
        return majorCache.getOrPut(key) {
            UniversitiesData.getMajorById(universityId, majorId)
        } ?: UniversitiesData.getMajorById(universityId, majorId)
    }

    /**
     * 搜索高校
     */
    fun searchUniversities(
        query: String,
        type: String? = null,
        subjectType: SubjectType? = null
    ): List<University> {
        return getAllUniversities().filter { university ->
            val matchesQuery = query.isBlank() ||
                university.name.contains(query, ignoreCase = true) ||
                university.shortName.contains(query, ignoreCase = true)

            val matchesType = type == null

            val matchesSubject = subjectType == null ||
                (subjectType == SubjectType.PHYSICS && university.overviewScores.values.any { it.physicsAvg != null }) ||
                (subjectType == SubjectType.HISTORY && university.overviewScores.values.any { it.historyAvg != null })

            matchesQuery && matchesType && matchesSubject
        }
    }

    /**
     * 获取专业分数列表
     */
    fun getMajorScores(subjectType: SubjectType): List<MajorScoreInfo> {
        val result = mutableListOf<MajorScoreInfo>()
        getAllUniversities().forEach { university ->
            university.majors.forEach { major ->
                major.scores.forEach { (year, score) ->
                    if (score.subjectType == subjectType) {
                        result.add(
                            MajorScoreInfo(
                                universityId = university.id,
                                universityName = university.name,
                                majorId = major.id,
                                majorName = major.name,
                                year = year,
                                lowScore = score.lowScore,
                                avgScore = score.avgScore,
                                highScore = score.highScore,
                                provinceControlLine = score.provinceControlLine,
                                subjectType = subjectType
                            )
                        )
                    }
                }
            }
        }
        return result.sortedByDescending { it.avgScore }
    }

    /**
     * 清除缓存
     */
    fun clearCache() {
        universityCache.clear()
        majorCache.clear()
    }
}

/**
 * 专业分数信息
 */
data class MajorScoreInfo(
    val universityId: String,
    val universityName: String,
    val majorId: String,
    val majorName: String,
    val year: String,
    val lowScore: Int,
    val avgScore: Int,
    val highScore: Int,
    val provinceControlLine: Int,
    val subjectType: SubjectType
)

/**
 * 异步数据加载器
 */
class AsyncDataLoader<T>(
    private val loader: suspend () -> T
) {
    private var cached: T? = null
    private var isLoading = false

    suspend fun load(): T {
        if (cached != null) return cached!!
        if (isLoading) {
            // 等待加载完成
            while (isLoading) {
                kotlinx.coroutines.delay(50)
            }
            return cached ?: throw IllegalStateException("加载失败")
        }
        isLoading = true
        try {
            val result = loader()
            cached = result
            return result
        } finally {
            isLoading = false
        }
    }

    fun invalidate() {
        cached = null
    }
}

/**
 * 分页加载器
 */
class PagedLoader<T>(
    private val allItems: List<T>,
    private val pageSize: Int = 20
) {
    private var currentPage = 0
    private var loadedPages = mutableSetOf<Int>()

    fun getPage(page: Int): List<T> {
        if (!loadedPages.contains(page)) {
            loadedPages.add(page)
        }
        currentPage = page
        return allItems.drop(page * pageSize).take(pageSize)
    }

    fun loadNextPage(): List<T> {
        return getPage(currentPage + 1)
    }

    fun hasMorePages(): Boolean {
        return (currentPage + 1) * pageSize < allItems.size
    }

    fun reset() {
        currentPage = 0
        loadedPages.clear()
    }
}

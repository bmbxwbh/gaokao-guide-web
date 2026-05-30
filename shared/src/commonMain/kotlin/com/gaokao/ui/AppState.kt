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

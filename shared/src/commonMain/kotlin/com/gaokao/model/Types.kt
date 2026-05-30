package com.gaokao.model

enum class UniversityType {
    COMPREHENSIVE,
    SCIENCE,
    MEDICAL,
    NORMAL,
    FINANCE,
    OTHERS
}

enum class ImageCategory {
    SCENERY,
    DORMITORY,
    DINING_HALL,
    BUILDING
}

enum class DegreeType {
    BACHELOR,
    MASTER,
    DOCTOR
}

enum class RequirementType {
    MUST,
    ONE_OF,
    RECOMMENDED,
    NO_REQUIREMENT
}

enum class BatchType {
    FIRST,
    SECOND,
    BATCH_1,
    BATCH_2
}

enum class SubjectType {
    PHYSICS,
    HISTORY,
    COMPREHENSIVE
}

enum class AdmissionProbability {
    SAFE,
    SURE,
    STABLE,
    STRETCH,
    RISKY
}

data class Location(
    val district: String,
    val city: String? = null,
    val province: String? = null
)

data class CampusImage(
    val id: String,
    val path: String,
    val category: ImageCategory,
    val description: String? = null
)

data class YearOverviewScore(
    val physicsLow: Int? = null,
    val physicsAvg: Int? = null,
    val physicsHigh: Int? = null,
    val historyLow: Int? = null,
    val historyAvg: Int? = null,
    val historyHigh: Int? = null
)

data class MajorScore(
    val batch: BatchType,
    val subjectType: SubjectType,
    val lowScore: Int,
    val avgScore: Int,
    val highScore: Int,
    val provinceControlLine: Int,
    val planCount: Int? = null,
    val actualCount: Int? = null
)

data class Major(
    val id: String,
    val code: String,
    val name: String,
    val department: String,
    val degree: DegreeType,
    val duration: Int,
    val subjectRequirement: SubjectType? = null,
    val introduction: String,
    val trainingGoal: String,
    val mainCourses: List<String>,
    val employmentDirections: List<String>,
    val furtherStudyDirections: List<String>,
    val scores: Map<String, MajorScore>,
    val relatedMajors: List<String>,
    val tags: List<String>? = null
)

data class University(
    val id: String,
    val name: String,
    val shortName: String,
    val logo: String,
    val type: UniversityType,
    val location: Location,
    val address: String,
    val phone: String,
    val website: String,
    val description: String,
    val foundingYear: Int,
    val department: String,
    val keyDisciplines: List<String>,
    val images: List<CampusImage>,
    val majors: List<Major>,
    val overviewScores: Map<String, YearOverviewScore>
)

data class Favorite(
    val id: String,
    val type: String,
    val targetId: String,
    val universityId: String? = null,
    val timestamp: Long
)

data class ComparisonItem(
    val id: String,
    val type: String,
    val targetId: String,
    val universityId: String? = null
)

data class UserScores(
    val totalScore: Int,
    val chinese: Int,
    val math: Int,
    val english: Int,
    val physics: Int? = null,
    val chemistry: Int? = null,
    val biology: Int? = null,
    val history: Int? = null,
    val geography: Int? = null,
    val politics: Int? = null,
    val subjectType: SubjectType
)

data class InterestCategory(
    val id: String,
    val name: String,
    val icon: String
)

data class InterestTag(
    val id: String,
    val name: String,
    val categoryId: String,
    val relatedMajorTags: List<String>
)

data class RecommendationResult(
    val universityId: String,
    val universityName: String,
    val universityShortName: String,
    val majorId: String,
    val majorName: String,
    val majorCode: String,
    val year: String,
    val probability: AdmissionProbability,
    val matchScore: Int,
    val avgScore: Int,
    val lowScore: Int,
    val subjectType: SubjectType,
    val explanation: String? = null,
    val aiSuggestion: String? = null
)

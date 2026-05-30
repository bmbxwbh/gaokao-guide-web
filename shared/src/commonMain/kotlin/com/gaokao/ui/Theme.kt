package com.gaokao.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import top.yukonga.miuix.kmp.theme.ColorSchemeMode
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.ThemeController

@Composable
fun GaokaoGuideTheme(
    content: @Composable () -> Unit
) {
    val controller = remember { ThemeController(ColorSchemeMode.System) }
    MiuixTheme(
        controller = controller,
        content = content
    )
}

fun getUniversityTypeName(type: com.gaokao.model.UniversityType): String {
    return when (type) {
        com.gaokao.model.UniversityType.COMPREHENSIVE -> "综合类"
        com.gaokao.model.UniversityType.SCIENCE -> "理工类"
        com.gaokao.model.UniversityType.MEDICAL -> "医药类"
        com.gaokao.model.UniversityType.NORMAL -> "师范类"
        com.gaokao.model.UniversityType.FINANCE -> "财经类"
        com.gaokao.model.UniversityType.OTHERS -> "其他"
    }
}

fun getSubjectTypeName(type: com.gaokao.model.SubjectType): String {
    return when (type) {
        com.gaokao.model.SubjectType.PHYSICS -> "物理类"
        com.gaokao.model.SubjectType.HISTORY -> "历史类"
        com.gaokao.model.SubjectType.COMPREHENSIVE -> "综合改革"
    }
}

fun getBatchName(batch: com.gaokao.model.BatchType): String {
    return when (batch) {
        com.gaokao.model.BatchType.FIRST, com.gaokao.model.BatchType.BATCH_1 -> "本科一批"
        com.gaokao.model.BatchType.SECOND, com.gaokao.model.BatchType.BATCH_2 -> "本科二批"
    }
}

fun getProbabilityText(probability: com.gaokao.model.AdmissionProbability): String {
    return when (probability) {
        com.gaokao.model.AdmissionProbability.SAFE, com.gaokao.model.AdmissionProbability.SURE -> "稳妥"
        com.gaokao.model.AdmissionProbability.STABLE -> "推荐"
        com.gaokao.model.AdmissionProbability.STRETCH -> "冲刺"
        com.gaokao.model.AdmissionProbability.RISKY -> "风险"
    }
}

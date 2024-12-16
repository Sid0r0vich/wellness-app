package com.example.wellness.data

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.IntOffset
import com.example.wellness.R
import com.example.wellness.ui.navigation.EnterAdditional
import com.example.wellness.ui.navigation.EnterCredentials
import com.example.wellness.ui.navigation.EnterPersonal
import com.example.wellness.ui.navigation.Login
import java.time.LocalDate

object HomeScreenUIStorage {
    val panelLabels = listOf(
        R.string.my_documents,
        R.string.dynamics,
        R.string.health_report
    )
    val panelImageIds = listOf(
        R.drawable.docs,
        R.drawable.dynamics,
        R.drawable.report
    )
    val panelValues = listOf(42, 13)
    val panelIcons = listOf(
        R.drawable.trace,
        R.drawable.heart
    )
}

object IndicatorStorage {
    private val indicators: Map<String, Indicator> = mapOf(
        "ferritin" to Indicator(
            nameId = R.string.ferritin,
            values = listOf(19f, 20.5f, 24f, 19.7f, 22.7f),
            referenceValues = 15f..204f
        ),
        "vitamin_D" to Indicator(
            nameId = R.string.vitamin_D,
            values = listOf(34f, 35.7f, 37f, 38f, 35f, 38f),
            referenceValues = 30f..100f
        ),
        "vitamin_B12" to Indicator(
            nameId = R.string.vitamin_B12,
            values = listOf(84f, 85.7f, 87f, 88f, 85f, 105f, 130f, 111f, 115f),
            referenceValues = 30f..100f
        ),
        "vitamin_B6" to Indicator(
            nameId = R.string.vitamin_B6,
            values = listOf(10f, 12.5f, 33f, 41f, 27.5f, 20f, 21.4f, 23.7f, 27.1f),
            referenceValues = 8.7f..27.2f
        )
    )

    fun getAll(): Map<String, Indicator> = indicators
}

object StepByStepRegistrationUIStorage {
    private const val SLIDE_DURATION = 500
    val stepByStepScreenRoutes: List<String> = listOf(
        Login.route,
        EnterCredentials.route,
        EnterPersonal.route,
        EnterAdditional.route
    )
    val animationSpec: FiniteAnimationSpec<IntOffset> = tween(durationMillis = SLIDE_DURATION)
}

object DocumentStorage {
    private val documents: List<Document> = listOf(
        Document(R.string.analysis_1, LocalDate.parse("2022-02-15")),
        Document(R.string.analysis_2, LocalDate.parse("2023-03-11")),
        Document(R.string.analysis_3, LocalDate.parse("2023-11-03")),
        Document(R.string.analysis_4, LocalDate.parse("2024-07-06")),
        Document(R.string.analysis_5, LocalDate.parse("2024-09-30")),
        Document(R.string.analysis_5, LocalDate.parse("2024-10-28"))
    )

    fun getAll(): List<Document> = documents
}
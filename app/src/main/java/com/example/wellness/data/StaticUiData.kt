package com.example.wellness.data

import com.example.wellness.R

object HomeScreenData {
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

data class Indicator(
    val nameId: Int,
    val values: List<Float>,
    val referenceValues: ClosedFloatingPointRange<Float>
)

object DynamicScreenData {
    val indicators: List<Indicator> = listOf(
        Indicator(
            nameId = R.string.ferritin,
            values = listOf(19f, 20.5f, 24f, 19.7f, 22.7f),
            referenceValues = 15f..204f
        ),
        Indicator(
            nameId = R.string.vitamin_D,
            values = listOf(34f, 35.7f, 37f, 38f, 35f, 38f),
            referenceValues = 30f..100f
        ),
        Indicator(
            nameId = R.string.vitamin_B12,
            values = listOf(84f, 85.7f, 87f, 88f, 85f, 105f, 130f, 111f, 115f),
            referenceValues = 30f..100f
    )
    )
}
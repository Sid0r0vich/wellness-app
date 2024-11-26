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

object DynamicScreenData {
    val graphLabels = listOf(
        R.string.ferritin,
        R.string.vitamin_D
    )
    val graphValues: List<Float> = listOf(22.7f, 38f)
    val indicatorValues: List<List<Float>> = listOf(
        listOf(19f, 20.5f, 24f, 19.7f), listOf(34f, 35.7f, 37f, 38f, 35f)
    )
}
package com.example.wellness.data

import com.example.wellness.R

object HomeScreenData {
    val panelLabels = arrayOf(
        R.string.my_documents,
        R.string.dynamics,
        R.string.health_report
    )
    val panelImageIds = arrayOf(
        R.drawable.docs,
        R.drawable.dynamics,
        R.drawable.report
    )
}

object DynamicScreenData {
    val graphLabels = arrayOf(
        R.string.ferritin,
        R.string.vitamin_D
    )

    val graphValues: Array<Float> = arrayOf(22.7f, 38f)
}
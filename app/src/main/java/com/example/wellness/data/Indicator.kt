package com.example.wellness.data

import com.example.wellness.R

data class Indicator(
    val nameId: Int,
    val values: List<Float>,
    val referenceValues: ClosedFloatingPointRange<Float>
)

object InitIndicator {
    val indicator = Indicator(
        nameId = R.string.init_indicator,
        values = listOf(),
        referenceValues = 0f..0f
    )
}
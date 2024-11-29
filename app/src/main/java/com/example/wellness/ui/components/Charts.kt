package com.example.wellness.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer

@Composable
fun IndicatorGraph(
    values: List<Float>,
    referenceValues: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier
) {
    val colors = listOf(Color.Green, Color.Red)
    val minValue = values.min()
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction { lineSeries { series( values.map { value -> value - minValue } ) } }
    }

    CartesianChartHost(
        rememberCartesianChart(
            rememberLineCartesianLayer(
                LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        fill = LineCartesianLayer.LineFill.single(fill(
                            if (values.last() in referenceValues) Color.Green else Color.Red
                        )),
                        pointConnector = remember { LineCartesianLayer.PointConnector.cubic(curvature = 0f) },
                    )
                )
            ),
        ),
        modelProducer,
        modifier = modifier
            .height(100.dp)
            .padding(PaddingValues(LocalBoardPadding.current))
    )
}

@Composable
fun HealthReportDonutChart(
    value: Float,
    modifier: Modifier = Modifier,
    startRadiusOuter: Dp = 140.dp,
    chartBarWidth: Dp = 22.dp,
    animDuration: Int = 1000,
    color: Color = Color.Gray
) {
    val increase = 1.5f
    val revolutionCount = 1
    val radiusOuter = startRadiusOuter * increase
    var animationPlayed by remember { mutableStateOf(false) }

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 360f * revolutionCount else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier
            .padding(PaddingValues(chartBarWidth / 2))
            .fillMaxWidth()
            .height(radiusOuter),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier = Modifier
                .size(animateSize.dp)
                .offset { IntOffset.Zero }
                .size(radiusOuter)
                .rotate(animateRotation)
        ) {
            drawArc(
                color = color,
                startAngle = +(100 - value) * 3.6f - 90f,
                sweepAngle = value * 3.6f,
                useCenter = false,
                style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}
package com.example.wellness.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wellness.data.Indicator

@Composable
fun IndicatorButton(
    modifier: Modifier = Modifier,
    painter: Painter
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(110.dp)
                .padding(20.dp)
        )
    }
}

@Composable
fun IndicatorsButtonPanel(
    modifier: Modifier = Modifier,
    paddingGrid: Dp,
    painters: List<Painter>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        painters.forEachIndexed { idx, painter ->
            IndicatorButton(
                modifier = Modifier.weight(1.0F),
                painter = painter
            )
            if (idx != painters.size - 1) DefaultSpacer()
        }
    }
}

@Composable
fun IndicatorCard(
    name: String,
    values: List<Float>,
    referenceValues: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
) {
    val value: Float? = values.lastOrNull()
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column(
            modifier = Modifier.padding(LocalBoardPadding.current)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = name, style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = value?.toString() ?: "--",
                    style = MaterialTheme.typography.headlineLarge,
                    color = value?.let { when(value) {
                        in referenceValues -> Color.Green
                        else -> Color.Red
                    } } ?: Color.Transparent
                )
            }
            DefaultSpacer()
            IndicatorGraphCard(
                values,
                referenceValues,
            )
        }
    }
}

@Composable
fun IndicatorGraphCard(
    values: List<Float>,
    referenceValues: ClosedFloatingPointRange<Float>
) {
    Surface(
        shape = MaterialTheme.shapes.medium
    ) {
        IndicatorGraph(values, referenceValues)
    }
}

@Composable
fun Indicator.Card() {
    DefaultSpacer()
    IndicatorCard(
        name = stringResource(this.nameId),
        values = this.values,
        referenceValues = this.referenceValues
    )
}
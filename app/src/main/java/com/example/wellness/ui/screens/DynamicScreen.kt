package com.example.wellness.ui.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wellness.R
import com.example.wellness.data.DynamicScreenData
import com.example.wellness.ui.components.DefaultSpacer
import com.example.wellness.ui.components.IndicatorGraph
import com.example.wellness.ui.components.LocalBoardPadding
import com.example.wellness.ui.components.LocalGridPadding
import com.example.wellness.ui.viewModels.DynamicViewModel

@Composable
fun DynamicScreen(
    modifier: Modifier = Modifier,
    viewModel: DynamicViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    UserScreen(uiState) {
        item {
            DefaultSpacer()
            IndicatorsButtonPanel(
                paddingGrid = LocalGridPadding.current * 2,
                painters = listOf(
                    painterResource(R.drawable.trace),
                    painterResource(R.drawable.heart),
                    painterResource(R.drawable.blood)
                )
            )
        }
        item {
            DynamicScreenData.indicators.forEach { indicator ->
                val value = indicator.values.last()
                DefaultSpacer()
                IndicatorCard(
                    name = stringResource(indicator.nameId),
                    values = indicator.values,
                    value = value,
                    referenceValues = indicator.referenceValues
                )
            }
        }
    }
}

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
    value: Float,
    values: List<Float>,
    referenceValues: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
) {
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
                    text = value.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = when(value) {
                        in referenceValues -> Color.Green
                        else -> Color.Red
                    }
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
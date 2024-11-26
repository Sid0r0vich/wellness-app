package com.example.wellness.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wellness.R
import com.example.wellness.data.DynamicScreenData
import com.example.wellness.ui.components.DefaultSpacer
import com.example.wellness.ui.components.HomeUserCard
import com.example.wellness.ui.components.IndicatorGraph
import com.example.wellness.ui.components.LocalBoardPadding
import com.example.wellness.ui.components.LocalGridPadding
import com.example.wellness.ui.viewModels.AppViewModelProvider
import com.example.wellness.ui.viewModels.DynamicViewModel

@Composable
fun DynamicScreen(
    modifier: Modifier = Modifier,
    viewModel: DynamicViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    DefaultScreen {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxHeight()
        ) {
            item {
                HomeUserCard(userName = uiState.userName)
            }
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
                (DynamicScreenData.graphLabels zip DynamicScreenData.graphValues).forEach { item ->
                    DefaultSpacer()
                    IndicatorCard(
                        name = stringResource(item.first),
                        values = DynamicScreenData.ferritinValues,
                        value = item.second
                    )
                }
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
                Text(text = value.toString(), style = MaterialTheme.typography.headlineLarge)
            }
            DefaultSpacer()
            IndicatorGraphCard(values)
        }
    }
}

@Composable
fun IndicatorGraphCard(values: List<Float>) {
    Surface(
        modifier = Modifier.padding(PaddingValues(LocalBoardPadding.current)),
        color = Color.Gray
    ) {
        IndicatorGraph(values)
    }
}
package com.example.wellness.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wellness.R
import com.example.wellness.data.Indicator
import com.example.wellness.ui.components.Card
import com.example.wellness.ui.components.DefaultSpacer
import com.example.wellness.ui.components.HealthReportDonutChart
import com.example.wellness.ui.theme.BadColor
import com.example.wellness.ui.theme.GoodColor
import com.example.wellness.ui.theme.LightBadColor
import com.example.wellness.ui.theme.LightGoodColor
import com.example.wellness.ui.theme.LightTolerableColor
import com.example.wellness.ui.theme.TolerableColor
import com.example.wellness.ui.viewModels.HealthReportViewModel

@Composable
fun HealthReportScreen(
    modifier: Modifier = Modifier,
    viewModel: HealthReportViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val indicators: List<Indicator> = viewModel.getIndicatorStateFlows()
        .map { indicatorStateFlow -> indicatorStateFlow.collectAsState().value }
    val badIndicators = indicators.filter { indicator ->
        indicator.values.lastOrNull()?.let { last ->
            last !in indicator.referenceValues
        } ?: false
    }
    val totalCount = indicators.size
    val goodIndicatorsCount = totalCount - badIndicators.size

    UserScreen(uiState) {
        item {
            DonutChartReport(count = goodIndicatorsCount, total = totalCount)
        }
        item {
            DefaultSpacer()
            Text(
                text = stringResource(R.string.indicator_attention),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
        badIndicators.forEach { indicator -> item { indicator.Card() } }
    }
}

@Composable
fun DonutChartReport(
    count: Int,
    total: Int
) {
    val chartColor = when (count) {
        in 0..total / 3 -> LightBadColor
        in total / 3 + 1..total * 2 / 3 -> LightTolerableColor
        else -> LightGoodColor
    }
    val textColor = when (count) {
        in 0..total / 3 -> BadColor
        in total / 3 + 1..total * 2 / 3 -> TolerableColor
        else -> GoodColor
    }

    DefaultSpacer()
    Text(
        text = LocalContext.current
            .getString(R.string.health_report_header, count, total),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = textColor,
        textAlign = TextAlign.Center
    )
    DefaultSpacer()
    HealthReportDonutChart(
        value = count.toFloat() / total * 100,
        color = chartColor
    )
}
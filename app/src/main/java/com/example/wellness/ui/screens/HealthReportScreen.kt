package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wellness.R
import com.example.wellness.ui.components.DefaultSpacer
import com.example.wellness.ui.components.HealthReportDonutChart
import com.example.wellness.ui.components.HomeUserCard
import com.example.wellness.ui.viewModels.HealthReportViewModel

@Composable
fun HealthReportScreen(
    modifier: Modifier = Modifier,
    viewModel: HealthReportViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val count = 7
    val total = 8

    DefaultScreen {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            item {
                HomeUserCard(userName = uiState.userName)
            }
            item {
                DonutChartReport(count = count, total = total)
            }
        }
    }
}

@Composable
fun DonutChartReport(
    count: Int,
    total: Int
) {
    val chartColor = when (count) {
        in 0..total / 3 -> Color.Red
        in total / 3 + 1..total * 2 / 3 -> Color(0xFFFF9400)
        else -> Color(0xFFABE5B7)
    }
    val textColor = when (count) {
        in 0..total / 3 -> Color(	0xFFFC3F4D)
        in total / 3 + 1..total * 2 / 3 -> Color(0xFFFF9400)
        else -> Color(0xFF7FAB88)
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
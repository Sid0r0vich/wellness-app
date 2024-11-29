package com.example.wellness.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wellness.R
import com.example.wellness.data.Indicator
import com.example.wellness.ui.components.Card
import com.example.wellness.ui.components.DefaultSpacer
import com.example.wellness.ui.components.IndicatorsButtonPanel
import com.example.wellness.ui.components.LocalGridPadding
import com.example.wellness.ui.viewModels.DynamicViewModel

@Composable
fun DynamicScreen(
    modifier: Modifier = Modifier,
    viewModel: DynamicViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val indicators: List<Indicator> = viewModel.getIndicatorStateFlows()
            .map { indicatorStateFlow -> indicatorStateFlow.collectAsState().value }

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
        indicators.forEach { indicator ->
            item { indicator.Card() }
        }
    }
}
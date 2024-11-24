package com.example.wellness.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wellness.R
import com.example.wellness.data.HomeScreenData
import com.example.wellness.ui.components.UserCard
import com.example.wellness.ui.viewModels.AppViewModelProvider
import com.example.wellness.ui.viewModels.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
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
                Spacer(modifier = Modifier.padding(PaddingValues(LocalGridPadding.current)))
                IndicatorsPanel(
                    paddingGrid = LocalGridPadding.current * 2,
                    values = listOf(42, 13),
                    painters = listOf(
                        painterResource(R.drawable.trace),
                        painterResource(R.drawable.heart)
                    )
                )
            }
            items(HomeScreenData.panelLabels zip HomeScreenData.panelImageIds) { panel ->
                Spacer(modifier = Modifier.padding(PaddingValues(LocalGridPadding.current)))
                AppPanel(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(panel.first),
                    painter = painterResource(panel.second)
                )
            }
        }
    }
}

@Composable
fun HomeUserCard(
    modifier: Modifier = Modifier,
    userName: String
) {
    UserCard(
        modifier = modifier.fillMaxWidth(),
        userName = userName,
        avatarModifier = Modifier.size(110.dp)
    )
}

@Composable
fun AppPanel(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .padding(PaddingValues(
                    horizontal = 25.dp,
                    vertical = 20.dp,
                )),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier,
            )
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
            )
        }
    }
}

@Composable
fun IndicatorsPanel(
    modifier: Modifier = Modifier,
    paddingGrid: Dp,
    values: List<Int>,
    painters: List<Painter>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        (values zip painters).forEachIndexed { idx, indicator ->
            AppPanel(
                modifier = Modifier.weight(1.0F),
                text = indicator.first.toString(),
                painter = indicator.second
            )
            if (idx != values.size - 1) Spacer(modifier = Modifier.width(paddingGrid))
        }
    }
}
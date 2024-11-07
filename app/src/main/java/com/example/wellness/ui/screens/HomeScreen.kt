package com.example.wellness.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wellness.R
import com.example.wellness.ui.AppViewModelProvider

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
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
    val paddingGrid = PaddingValues(
        vertical = 10.dp,
        horizontal = 20.dp
    )

    val uiState by viewModel.uiState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserCard(
                modifier = Modifier
                    .padding(PaddingValues(bottom = 25.dp))
                    .padding(paddingGrid)
                    .fillMaxWidth(),
                userName = uiState.userName
            )
            IndicatorsPanel(
                modifier = Modifier
                    .padding(paddingGrid),
                paddingGrid = 20.dp,
                values = listOf(42, 13),
                painters = listOf(
                    painterResource(R.drawable.trace),
                    painterResource(R.drawable.heart)
                )
            )
            (panelLabels zip panelImageIds).forEach { panel ->
                AppPanel(
                    modifier = Modifier
                        .padding(paddingGrid)
                        .fillMaxWidth(),
                    text = stringResource(panel.first),
                    painter = painterResource(panel.second)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    userName: String = stringResource(R.string.user_name)
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .padding(PaddingValues(8.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(PaddingValues(10.dp))
            ) {
                Text(
                    text = "Hello, $userName!",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(PaddingValues(bottom = 5.dp))
                )
                Text(
                    text = stringResource(R.string.be_wellness),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(PaddingValues(top = 5.dp)),
                    fontWeight = FontWeight.Bold
                )
            }
            Image(
                painter = painterResource(R.drawable.user),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        }
    }
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
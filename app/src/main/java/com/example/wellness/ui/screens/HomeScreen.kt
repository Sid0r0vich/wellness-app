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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
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
import com.example.wellness.data.HomeScreenData
import com.example.wellness.ui.AppViewModelProvider

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridPadding: Dp = 10.dp
    val userCardPadding: Dp = 10.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(PaddingValues(gridPadding * 2))
    ) {
        LazyColumn {
            item {
                HomeUserCard(
                    userName = uiState.userName,
                )
            }
            item {
                Spacer(modifier = Modifier.padding(PaddingValues(userCardPadding)))
                IndicatorsPanel(
                    paddingGrid = gridPadding * 2,
                    values = listOf(42, 13),
                    painters = listOf(
                        painterResource(R.drawable.trace),
                        painterResource(R.drawable.heart)
                    )
                )
            }
            items(HomeScreenData.panelLabels zip HomeScreenData.panelImageIds) { panel ->
                Spacer(modifier = Modifier.padding(PaddingValues(gridPadding)))
                AppPanel(
                    modifier = Modifier
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
    userName: String = stringResource(R.string.user_name),
    addButton: Boolean = false,
    buttonOnClick: () -> Unit = {},
    avatarModifier: Modifier = Modifier
) {
    val paddingText: Dp = 5.dp
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(PaddingValues(20.dp))
            ) {
                Text(
                    text = "Hello, $userName!",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.padding(PaddingValues(paddingText)))
                Text(
                    text = stringResource(R.string.be_wellness),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (addButton) {
                    Spacer(modifier = Modifier.padding(PaddingValues(paddingText)))
                    Button(
                        onClick = buttonOnClick,
                    ) {
                        Text(text = stringResource(R.string.sign_out))
                    }
                }
            }
            Image(
                painter = painterResource(R.drawable.user),
                contentDescription = null,
                modifier = avatarModifier
                    .padding(PaddingValues(10.dp))
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        }
    }
}

@Composable
fun HomeUserCard(
    modifier: Modifier = Modifier,
    userName: String
) {
    UserCard(
        modifier = modifier
            .fillMaxWidth(),
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
package com.example.wellness.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wellness.R
import com.example.wellness.ui.theme.WellnessAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
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

    val paddingColumn = PaddingValues(
        vertical = 10.dp,
        horizontal = 20.dp
    )

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
                    .padding(PaddingValues(bottom = 40.dp))
                    .padding(paddingColumn)
                    .fillMaxWidth()
            )

            (panelLabels zip panelImageIds).forEach { panel ->
                AppPanel(
                    modifier = Modifier
                        .padding(paddingColumn)
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
fun UserCard(modifier: Modifier) {
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
                    text = "Hello, User!",
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
        modifier = modifier.width(250.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .padding(PaddingValues(horizontal = 25.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(
                    vertical = 42.dp
                ),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .size(64.dp)
            )
        }
    }
}
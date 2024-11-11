package com.example.wellness.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wellness.R
import com.example.wellness.ui.navigation.NavDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    currentScreen: NavDestination,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
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
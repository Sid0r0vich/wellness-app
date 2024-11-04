package com.example.wellness.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentScreen: NavDestination,
    onClick: (String) -> Unit,
) {
    NavigationBar(
        modifier = modifier
    ) {
        navDestinations
            .onEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(item.iconId),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(item.label)
                        )
                    },
                    selected = navDestinations.indexOf(currentScreen) == index,
                    onClick = {
                        onClick(item.route)
                    }
                )
            }
    }
}
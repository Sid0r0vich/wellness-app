package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.wellness.R

enum class Route {
    Home,
    Profile,
    Add,
    Chat,
    Notifications,
}
val navigationBarItemRoutes = arrayOf(
    "home",
    "profile",
    "add",
    "chat",
    "notifications"
)
val navigationBarItemLabels = arrayOf(
    R.string.bottom_navigation_home,
    R.string.bottom_navigation_profile,
    R.string.bottom_navigation_add,
    R.string.bottom_navigation_chat,
    R.string.bottom_navigation_notifications
)
val navigationBarItemImages = arrayOf(
    R.drawable.home,
    R.drawable.user_profile,
    R.drawable.add,
    R.drawable.chat,
    R.drawable.notify
)

data class TopLevelRoute(
    val route: String,
    val label: Int,
    val imageId: Int
)

val topLevelRoutes = navigationBarItemRoutes
    .mapIndexed { index, item ->
        TopLevelRoute(
            item,
            navigationBarItemLabels[index],
            navigationBarItemImages[index]
        )
    }

operator fun List<TopLevelRoute>.get(route: Route): String {
    return this[route.ordinal].route
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        modifier = modifier
    ) {
        topLevelRoutes
            .onEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(item.imageId),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(item.label)
                        )
                    },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
    }
}
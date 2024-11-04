package com.example.wellness.ui.navigation

import com.example.wellness.R

interface NavDestination {
    val label: Int
    val iconId: Int
    val route: String
}

object Home : NavDestination {
    override val label = R.string.bottom_navigation_home
    override val iconId = R.drawable.home
    override val route = "home"
}

object Profile : NavDestination {
    override val label = R.string.bottom_navigation_profile
    override val iconId = R.drawable.user_profile
    override val route = "profile"
}

object Add : NavDestination {
    override val label = R.string.bottom_navigation_add
    override val iconId = R.drawable.add
    override val route = "add"
}

object Chat : NavDestination {
    override val label = R.string.bottom_navigation_chat
    override val iconId = R.drawable.chat
    override val route = "chat"
}

object Notifications : NavDestination {
    override val label = R.string.bottom_navigation_notifications
    override val iconId = R.drawable.notify
    override val route = "notify"
}

val navDestinations = listOf(
    Home,
    Profile,
    Add,
    Chat,
    Notifications
)
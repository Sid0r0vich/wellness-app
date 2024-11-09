package com.example.wellness.ui.navigation

import com.example.wellness.R

interface NavDestination {
    val label: Int
    val route: String
}

interface NavBarDestination : NavDestination {
    val iconId: Int
}

object Home : NavBarDestination {
    override val label = R.string.bottom_navigation_home
    override val iconId = R.drawable.home
    override val route = "home"
}

object Profile : NavBarDestination {
    override val label = R.string.bottom_navigation_profile
    override val iconId = R.drawable.user_profile
    override val route = "profile"
}

object Add : NavBarDestination {
    override val label = R.string.bottom_navigation_add
    override val iconId = R.drawable.add
    override val route = "add"
}

object Chat : NavBarDestination {
    override val label = R.string.bottom_navigation_chat
    override val iconId = R.drawable.chat
    override val route = "chat"
}

object Notifications : NavBarDestination {
    override val label = R.string.bottom_navigation_notifications
    override val iconId = R.drawable.notify
    override val route = "notify"
}

object Login : NavDestination {
    override val label = R.string.navigation_login
    override val route = "login"
}

object Register : NavDestination {
    override val label = R.string.navigation_register
    override val route = "register"
}

val navBarDestinations = listOf(
    Home,
    Profile,
    Add,
    Chat,
    Notifications
)

val navDestinations = listOf(
    Login,
    Home,
    Profile,
    Add,
    Chat,
    Notifications
)
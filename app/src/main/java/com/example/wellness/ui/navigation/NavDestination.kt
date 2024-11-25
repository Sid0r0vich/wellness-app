package com.example.wellness.ui.navigation

import com.example.wellness.R

open class NavDestination(
    open val label: Int,
    open val route: String
) {
    operator fun plus(navDestination: NavDestination): NavDestination {
        return NavDestination(
            label = navDestination.label,
            route = this.route + "/" + navDestination.route
        )
    }
}

open class NavBarDestination(
    override val label: Int,
    override val route: String,
    open val iconId: Int
) : NavDestination(label = label, route = route)


object Auth : NavDestination(
    label = R.string.navigation_auth,
    route = "auth"
)
object User : NavDestination(
    label = R.string.navigation_user,
    route = "user"
)

object Login : NavDestination(
    label = R.string.navigation_login,
    route = "login"
)
object Register : NavDestination(
    label = R.string.navigation_register,
    route = "register"
)

object Home : NavBarDestination(
    label = R.string.bottom_navigation_home,
    iconId = R.drawable.home,
    route = "home"
)
object Profile : NavBarDestination(
    label = R.string.bottom_navigation_profile,
    iconId = R.drawable.user_profile,
    route = "profile"
)
object Add : NavBarDestination(
    label = R.string.bottom_navigation_add,
    iconId = R.drawable.add,
    route = "add"
)
object Chat : NavBarDestination(
    label = R.string.bottom_navigation_chat,
    iconId = R.drawable.chat,
    route = "chat"
)
object Notifications : NavBarDestination(
    label = R.string.bottom_navigation_notifications,
    iconId = R.drawable.notify,
    route = "notify"
)

object Dynamic : NavDestination(
    label = R.string.navigation_dynamic,
    route = "dynamic"
)

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
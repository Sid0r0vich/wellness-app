package com.example.wellness.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wellness.ui.screens.EmptyScreen
import com.example.wellness.ui.screens.HomeScreen
import com.example.wellness.ui.screens.LoginScreen
import com.example.wellness.ui.screens.ProfileScreen
import com.example.wellness.ui.screens.RegisterScreen

@Composable
fun MyHavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable(Home.route) { HomeScreen() }
        composable(Profile.route) {
            ProfileScreen { navController.navigateSingleTopWithPopUp(Login.route) }
        }
        navBarDestinations.drop(2).forEach { dest ->
            composable(dest.route) { EmptyScreen() }
        }
        composable("login") {
            LoginScreen (
                onPerformLogin = { navController.navigateSingleTopWithPopUp(Home.route) },
                onRegisterClick = { navController.navigateSingleTopWithPopUp(Register.route) }
            )
        }
        composable("register") {
            RegisterScreen (
                onPerformRegister = { navController.navigateSingleTopWithPopUp(Home.route) },
                onLoginClick = { navController.navigateSingleTopWithPopUp(Login.route) }
            )
        }
    }
}

fun NavHostController.navigateSingleTopWithPopUp(route: String) {
    val navController = this
    this.navigate(route) {
        this.popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

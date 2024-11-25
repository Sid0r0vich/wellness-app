package com.example.wellness.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.wellness.ui.screens.DynamicScreen
import com.example.wellness.ui.screens.EmptyScreen
import com.example.wellness.ui.screens.HomeScreen
import com.example.wellness.ui.screens.LoginScreen
import com.example.wellness.ui.screens.ProfileScreen
import com.example.wellness.ui.screens.RegisterScreen

@Composable
fun MyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = Auth.route,
        modifier = modifier
    ) {
        navigation(
            route = User.route,
            startDestination = Home.route
        ) {
            composable(Home.route) { HomeScreen { navController.navigateToDynamic() } }
            composable(Profile.route) {
                ProfileScreen { navController.navigateToAuth() }
            }
            navBarDestinations.drop(2).forEach { dest ->
                composable(dest.route) { EmptyScreen() }
            }
            composable(Dynamic.route) { DynamicScreen() }
        }
        navigation(
            route = Auth.route,
            startDestination = Login.route
        ) {
            composable(Login.route) {
                LoginScreen (
                    onPerformLogin = { navController.navigateToUser() },
                    onRegisterClick = { navController.navigateToRegister() }
                )
            }
            composable(Register.route) {
                RegisterScreen (
                    onPerformRegister = { navController.navigateToUser() },
                    onLoginClick = { navController.navigateToLogin() }
                )
            }
        }
    }
}
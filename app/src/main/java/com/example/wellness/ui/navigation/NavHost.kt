package com.example.wellness.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.wellness.ui.screens.DynamicScreen
import com.example.wellness.ui.screens.EmptyScreen
import com.example.wellness.ui.screens.HealthReportScreen
import com.example.wellness.ui.screens.HomeScreen
import com.example.wellness.ui.screens.LoginScreen
import com.example.wellness.ui.screens.ProfileScreen
import com.example.wellness.ui.screens.RegisterScreen
import com.example.wellness.ui.screens.register.EnterAdditionalScreen
import com.example.wellness.ui.screens.register.EnterCredentialsScreen
import com.example.wellness.ui.screens.register.EnterPersonalScreen

@Composable
fun MyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val slideDuration = 500
    NavHost(
        navController,
        startDestination = Auth.route,
        modifier = modifier
    ) {
        navigation(
            route = User.route,
            startDestination = Home.route
        ) {
            composable(Home.route) {
                HomeScreen(
                    onClicks = listOf(
                        {  },
                        { navController.navigateToDynamic() },
                        { navController.navigateToHealthReport() }
                    )
                )
            }
            composable(Profile.route) {
                ProfileScreen { navController.navigateToAuth() }
            }
            navBarDestinations.drop(2).forEach { dest ->
                composable(dest.route) { EmptyScreen() }
            }
            composable(Dynamic.route) { DynamicScreen() }
            composable(HealthReport.route) { HealthReportScreen() }
        }
        navigation(
            route = Auth.route,
            startDestination = Login.route
        ) {
            composable(
                Login.route,
                enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = slideDuration)) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(durationMillis = slideDuration)) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = slideDuration)) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis = slideDuration)) }
            ) {
                LoginScreen (
                    onPerformLogin = { navController.navigateToUser() },
                    onRegisterClick = { navController.navigateToNext() },
                )
            }
            composable(Register.route) {
                RegisterScreen (
                    onPerformRegister = { navController.navigateToUser() },
                    onLoginClick = { navController.popBackStack() }
                )
            }
            composable(
                EnterCredentials.route,
                enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = slideDuration)) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(durationMillis = slideDuration)) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = slideDuration)) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis = slideDuration)) }
            ) {
                EnterCredentialsScreen(
                    onLoginClick = { navController.navigateToNext() },
                    onNextClick = { navController.navigateToNext() }
                )
            }
            composable(
                EnterPersonal.route,
                enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = slideDuration)) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(durationMillis = slideDuration)) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = slideDuration)) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis = slideDuration)) }
            ) {
                EnterPersonalScreen { navController.navigateToNext() }
            }
            composable(
                EnterAdditional.route,
                enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis = slideDuration)) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(durationMillis = slideDuration)) },
                popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis = slideDuration)) },
                popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis = slideDuration)) }
            ) {
                EnterAdditionalScreen { navController.navigateToNext() }
            }
        }
    }
}
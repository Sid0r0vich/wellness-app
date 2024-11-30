package com.example.wellness.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.wellness.data.StepByStepRegistrationUIStorage.animationSpec
import com.example.wellness.data.StepByStepRegistrationUIStorage.stepByStepScreenRoutes
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
    val stepByStepScreenList: List<@Composable () -> Unit> = listOf(
        { LoginScreen(
            onPerformLogin = { navController.navigateToUser() },
            onRegisterClick = { navController.navigateToNext() },
        ) },
        { EnterCredentialsScreen(
            onLoginClick = { navController.navigateToNext() },
            onNextClick = { navController.navigateToNext() }
        ) },
        { EnterPersonalScreen { navController.navigateToNext() } },
        { EnterAdditionalScreen { navController.navigateToNext() } }
    )

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
            composable(Register.route) {
                RegisterScreen (
                    onPerformRegister = { navController.navigateToUser() },
                    onLoginClick = { navController.popBackStack() }
                )
            }
            composable(Login.route) {
                LoginScreen (
                    onPerformLogin = { navController.navigateToUser() },
                    onRegisterClick = { navController.navigateToNext() },
                )
            }
            (stepByStepScreenRoutes zip stepByStepScreenList).forEach { composable ->
                composable(
                    composable.first,
                    enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = animationSpec) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = animationSpec) },
                    popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = animationSpec) },
                    popExitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = animationSpec) }
                ) {
                    composable.second()
                }
            }
        }
    }
}
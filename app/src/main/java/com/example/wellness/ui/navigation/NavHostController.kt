package com.example.wellness.ui.navigation

import android.util.Log
import androidx.navigation.NavHostController

fun NavHostController.addLogger() {
    this.addOnDestinationChangedListener { controller, _, _ ->
        val routes = controller
            .currentBackStack.value
            .map { it.destination.route }
            .joinToString(", ")

        Log.d("BackStackLog", "BackStack: $routes")
    }
}

fun NavHostController.navigateWithPopUp(
    route: String,
    upTo: String,
    include: Boolean = false
) {
    this.navigate(route) {
        this.popUpTo(upTo) {
            saveState = true
            inclusive = include
        }
        restoreState = true
    }
}

fun NavHostController.navigateToUser() = navigateWithPopUp(User.route, Auth.route, true)
fun NavHostController.navigateToLogin() = navigateWithPopUp(Login.route, Auth.route, false)
fun NavHostController.navigateToRegister() = navigateWithPopUp(Register.route, Auth.route, false)
fun NavHostController.navigateToAuth() = navigateWithPopUp(Auth.route, User.route, true)
fun NavHostController.navigateToDynamic() = navigateWithPopUp(Dynamic.route, Home.route, false)
fun NavHostController.navigateFromHome(route: String) =
    this.navigate(route)
    {
        popUpTo(Home.route) {
            saveState = true
            inclusive = false
        }
        restoreState = true
        launchSingleTop = true
    }
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
    upTo: String
) {
    this.navigate(route) {
        this.popUpTo(upTo) {
            saveState = true
        }
        restoreState = true
    }
}

fun NavHostController.navigateToUser() = navigateWithPopUp(User.route, Auth.route)
fun NavHostController.navigateToLogin() = navigateWithPopUp(Login.route, Auth.route)
fun NavHostController.navigateToRegister() = navigateWithPopUp(Register.route, Auth.route)
fun NavHostController.navigateToAuth() = navigateWithPopUp(Auth.route, User.route)
fun NavHostController.navigateFromHome(route: String) =
    this.navigate(route) {
        popUpTo(Home.route)
        launchSingleTop = true
    }
package com.example.wellness.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wellness.ui.screens.EmptyScreen
import com.example.wellness.ui.screens.HomeScreen
import com.example.wellness.ui.screens.ProfileScreen

@Composable
fun MyHavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(Home.route) { HomeScreen() }
        composable(Profile.route) { ProfileScreen() }
        navDestinations.drop(2).forEach { dest ->
            composable(dest.route) { EmptyScreen() }
        }
    }
}
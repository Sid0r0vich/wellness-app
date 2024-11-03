package com.example.wellness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wellness.ui.screens.BottomNavigation
import com.example.wellness.ui.screens.HomeScreen
import com.example.wellness.ui.screens.MockScreen
import com.example.wellness.ui.screens.ProfileScreen
import com.example.wellness.ui.screens.Route
import com.example.wellness.ui.screens.TopAppBar
import com.example.wellness.ui.screens.get
import com.example.wellness.ui.screens.navigationBarItemRoutes
import com.example.wellness.ui.screens.topLevelRoutes
import com.example.wellness.ui.theme.WellnessAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WellnessAppTheme {
                MyApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    WellnessAppTheme {
        Scaffold(
            topBar = { TopAppBar(scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()) },
            bottomBar = { BottomNavigation(navController = navController) },
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = topLevelRoutes[Route.Home],
                modifier = Modifier.padding(innerPadding))
            {
                composable(topLevelRoutes[Route.Home]) {
                    HomeScreen()
                }
                composable(topLevelRoutes[Route.Profile]) {
                    ProfileScreen()
                }
                navigationBarItemRoutes.drop(2)
                    .forEach {
                    route -> composable(route) { MockScreen() }
                }
            }
        }
    }
}
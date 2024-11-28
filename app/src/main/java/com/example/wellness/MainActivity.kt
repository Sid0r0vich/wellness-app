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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wellness.ui.components.TopAppBar
import com.example.wellness.ui.navigation.BottomNavigationBar
import com.example.wellness.ui.navigation.Home
import com.example.wellness.ui.navigation.Login
import com.example.wellness.ui.navigation.MyNavHost
import com.example.wellness.ui.navigation.NavDestination
import com.example.wellness.ui.navigation.addLogger
import com.example.wellness.ui.navigation.navBarDestinations
import com.example.wellness.ui.navigation.navDestinations
import com.example.wellness.ui.navigation.navigateFromHome
import com.example.wellness.ui.theme.WellnessAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WellnessAppTheme {
                WellnessApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WellnessApp() {
    WellnessAppTheme {
        val registered = false
        val navController = rememberNavController()
        navController.addLogger()
        val currentScreen: NavDestination = navDestinations.find {
            navController
                .currentBackStackEntryAsState()
                .value
                ?.destination
                ?.route == it.route
        } ?: if (registered) Home else Login
        val bottomBarVisibility = currentScreen in navBarDestinations

        Scaffold(
            topBar = { TopAppBar(
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                currentScreen = currentScreen,
                ) },
            bottomBar = {
                if (bottomBarVisibility) BottomNavigationBar(
                    currentScreen = currentScreen
                ) { route ->
                    navController.navigateFromHome(route)
                }
            }
        ) { innerPadding ->
            MyNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
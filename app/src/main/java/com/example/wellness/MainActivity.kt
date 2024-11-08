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
import com.example.wellness.ui.navigation.BottomNavigationBar
import com.example.wellness.ui.navigation.Home
import com.example.wellness.ui.navigation.Login
import com.example.wellness.ui.navigation.MyHavHost
import com.example.wellness.ui.navigation.NavDestination
import com.example.wellness.ui.navigation.navBarDestinations
import com.example.wellness.ui.navigation.navDestinations
import com.example.wellness.ui.navigation.navigateSingleTopWithPopUp
import com.example.wellness.ui.screens.TopAppBar
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
    WellnessAppTheme {
        val registered = false
        val navController = rememberNavController()
        val currentScreen: NavDestination = navDestinations.find {
            val currentDestination = navController
                .currentBackStackEntryAsState()
                .value
                ?.destination
            it.route == currentDestination?.route
        } ?: if (registered) Home else Login
        val bottomBarVisibility = currentScreen in navBarDestinations

        Scaffold(
            topBar = { TopAppBar(scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()) },
            bottomBar = {
                if (bottomBarVisibility) BottomNavigationBar(
                    currentScreen = currentScreen
                ) { route ->
                    navController.navigateSingleTopWithPopUp(route)
                }
            }
        ) { innerPadding ->
            MyHavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
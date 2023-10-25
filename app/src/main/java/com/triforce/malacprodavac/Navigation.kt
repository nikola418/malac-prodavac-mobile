package com.triforce.malacprodavac
import androidx.compose.runtime.Composable
import androidx.navigation.NavType

import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.triforce.malacprodavac.presentation.login.LoginScreen
import com.triforce.malacprodavac.presentation.RegistrationScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route) {
            composable(route = Screen.LoginScreen.route) {
                LoginScreen(navController = navController)
            }
            composable(
                route = Screen.RegistrationScreen.route + "",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                        defaultValue = "Uros"
                        nullable = true
                    }
                )
            ) {entry ->
                RegistrationScreen(navController = navController)
            }
            composable(
                route = Screen.HomeScreen.route,

            ) {entry->

            }
    }
}
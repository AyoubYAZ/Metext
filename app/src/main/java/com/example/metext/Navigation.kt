package com.example.metext

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            Splashscreen(navController = navController)
        }
        composable("main_screen") {
            Mainscreen(navController = navController)
        }
        composable("summarize") {
            SummarizeText()

        }
        composable("analyse"){
            Analyse()
        }
        composable("extract"){
            ExtractTextFromFileScreen()
        }


    }
}
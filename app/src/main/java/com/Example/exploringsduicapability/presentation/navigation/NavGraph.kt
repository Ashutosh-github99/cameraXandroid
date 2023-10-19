package com.Example.exploringsduicapability.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Example.exploringsduicapability.presentation.Screens.CameraScreen


@Composable
fun NavContainer() {


    val navController = rememberNavController()


//    NavHost(
//        modifier = Modifier.fillMaxSize(),
//        navController = navController,
//        startDestination = Destinations.home
//    ) {
//        composable(route = Destinations.home){
//            CameraScreen(parenttNavController = navController)
//        }
//    }
}
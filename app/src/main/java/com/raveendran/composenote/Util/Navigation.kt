package com.raveendran.composenote.Util

import Screen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.raveendran.composenote.ui.screens.AddNoteScreen
import com.raveendran.composenote.ui.screens.NoteDetailScreen
import com.raveendran.composenote.ui.screens.NoteScreen
import com.raveendran.composenote.ui.screens.SplashScreen

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(
            route = Screen.SplashScreen.route,
        ) {
            SplashScreen(navController = navController)
        }
        composable(
            route = Screen.NoteScreen.route,
            enterTransition = { _, _ ->
                fadeIn(animationSpec = tween(500))
            },
            exitTransition = { _, _ ->
                slideOutHorizontally({ 1000 })
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally({ 1000 })
            },
        ) {
            NoteScreen(navController = navController)
        }
        composable(
            route = Screen.NoteDetailsScreen.route + "/{title}",
            enterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { 400 })
            },
            exitTransition = { _, _ ->
                slideOutHorizontally({ 1000 })
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally({ 1000 })
            },
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
            )
        ) { entry ->
            NoteDetailScreen(
                navController = navController,
                id = entry.arguments?.getString("title")
//                desc = entry.arguments?.getString("desc")!!,
            )
        }
        composable(
            route = Screen.AddNoteScreen.route,
            enterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { 400 })
            },
            exitTransition = { _, _ ->
                slideOutHorizontally({ 1000 })
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally({ 1000 })
            },
        ) {
            AddNoteScreen(navController)
        }
    }
}
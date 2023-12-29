package com.example.notesapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.screen.screens.NoteScreen
import com.example.notesapp.screen.screens.NotesListScreen
import com.example.notesapp.screen.screens.SignInScreen
import com.example.notesapp.screen.screens.SignUpScreen
import com.example.notesapp.screen.screens.SplashScreen

@Composable
fun AppNavigation() {

    val appState = rememberAppState()

    Scaffold { innerPaddingModifier ->
        NavHost(
            navController = appState.navController,
            startDestination = Routes.SPLASH_SCREEN,
            modifier = Modifier.padding(innerPaddingModifier)
        ) {
            notesGraph(appState)
        }
    }
}


@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        AppState(navController)

    }

fun NavGraphBuilder.notesGraph(appState: AppState) {

    composable(Routes.NOTES_LIST_SCREEN) {
        NotesListScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(
        route = "${Routes.NOTE_SCREEN}${Routes.NOTE_ID_ARG}",
        arguments = listOf(navArgument(Routes.NOTE_ID) { defaultValue = Routes.NOTE_DEFAULT_ID })
    ) {
        NoteScreen(
            noteId = it.arguments?.getString(Routes.NOTE_ID) ?: Routes.NOTE_DEFAULT_ID,
            popUpScreen = { appState.popUp() },
            restartApp = { route -> appState.clearAndNavigate(route) }
        )
    }

    composable(Routes.SIGN_IN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(Routes.SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(Routes.SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
}


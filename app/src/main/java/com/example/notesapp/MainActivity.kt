package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.notesapp.navigation.AppNavigation
import com.example.notesapp.navigation.Routes
import com.example.notesapp.screen.screens.NoteScreen
import com.example.notesapp.screen.screens.NotesListScreen
import com.example.notesapp.screen.screens.SignInScreen
import com.example.notesapp.screen.screens.SignUpScreen
import com.example.notesapp.ui.theme.NotesAppTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
       // configureFirebaseServices()
        setContent {
            NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //NoteScreen(noteId = "1", popUpScreen = { }, restartApp = {})
                    //NotesListScreen(restartApp = {}, openScreen = {})
                    //SignUpScreen({ _, _ -> })
                    //SignInScreen({ _, _ -> })
                    AppNavigation()
                }
            }
        }
    }

    private fun configureFirebaseServices() {
        if (BuildConfig.DEBUG) {
            Firebase.auth.useEmulator(Routes.LOCALHOST, Routes.AUTH_PORT)
            Firebase.firestore.useEmulator(Routes.LOCALHOST, Routes.FIRESTORE_PORT)
        }
    }
}


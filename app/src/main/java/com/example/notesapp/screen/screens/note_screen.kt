package com.example.notesapp.screen.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notesapp.model.getTitle
import com.example.notesapp.screen.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    noteId: String,
    popUpScreen: () -> Unit,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val note = viewModel.note.collectAsState()

    LaunchedEffect(Unit) { viewModel.initialize(noteId, restartApp) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = note.value.getTitle()) },
            actions = {
                IconButton(onClick = { viewModel.saveNote(popUpScreen) }) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Save note")
                }
                IconButton(onClick = { viewModel.deleteNote(popUpScreen) }) {
                    Icon(Icons.Filled.Delete, "Delete note")
                }
            }
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = note.value.text,
                onValueChange = { viewModel.updateNote(it) },
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewNoteScreen() {
    NoteScreen(noteId = "1", popUpScreen = { /*TODO*/ }, restartApp = {})
}
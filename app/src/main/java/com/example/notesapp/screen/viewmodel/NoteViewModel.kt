package com.example.notesapp.screen.viewmodel

import com.example.notesapp.navigation.Routes
import com.example.notesapp.model.Note
import com.example.notesapp.model.service.AccountService
import com.example.notesapp.model.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val storageService: StorageService,
    private val accountService: AccountService
) : NotesAppViewModel() {

    val note = MutableStateFlow(DEFAULT_NOTE)

    fun initialize(noteId: String, restartApp: (String) -> Unit) {
        launchCatching {
            note.value = storageService.readNote(noteId) ?: DEFAULT_NOTE
        }

        observeAuthenticationState(restartApp)

    }

    private fun observeAuthenticationState(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect { user ->
                if (user == null) restartApp(Routes.SPLASH_SCREEN)
            }
        }
    }

    fun updateNote(newText: String) {
        note.value = note.value.copy(text = newText)
    }

    fun saveNote(popUpScreen: () -> Unit) {
        launchCatching {
            if (note.value.id == Routes.NOTE_DEFAULT_ID) {
                storageService.createNote(note.value)
            } else {
                storageService.updateNote(note.value)
            }
        }
        popUpScreen()

    }

    fun deleteNote(popUpScreen: () -> Unit) {
        launchCatching {
            storageService.deleteNote(note.value.id)
        }
        popUpScreen()
    }


    companion object {
        private val DEFAULT_NOTE = Note(Routes.NOTE_DEFAULT_ID, "my_note")
    }
}
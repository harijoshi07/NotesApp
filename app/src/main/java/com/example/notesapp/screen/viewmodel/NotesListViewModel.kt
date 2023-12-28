package com.example.notesapp.screen.viewmodel

import com.example.notesapp.Routes
import com.example.notesapp.model.Note
import com.example.notesapp.model.service.AccountService
import com.example.notesapp.model.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val accountService: AccountService,
    storageService: StorageService
) : NotesAppViewModel() {

    val notes = storageService.notes

    fun initialize(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.currentUser.collect { user ->
                if (user == null) restartApp(Routes.SPLASH_SCREEN)
            }
        }
    }

    fun onAddClick(openScreen: (String) -> Unit) {
        openScreen("${Routes.NOTE_SCREEN}?${Routes.NOTE_ID}=${Routes.NOTE_DEFAULT_ID}")
    }

    fun onNoteClick(openScreen: (String) -> Unit, note: Note) {
        openScreen("${Routes.NOTE_SCREEN}?${Routes.NOTE_ID}=${note.id}")
    }

    fun onSignOutClick() {
        launchCatching {
            accountService.signOut()
        }
    }

    fun onDeleteAccountClick() {
        launchCatching {
            accountService.deleteAccount()
        }
    }
}
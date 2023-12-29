package com.example.notesapp.screen.viewmodel

import com.example.notesapp.model.service.AccountService
import com.example.notesapp.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
) : NotesAppViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.hasUser()) openAndPopUp(Routes.NOTES_LIST_SCREEN, Routes.SPLASH_SCREEN)
        else openAndPopUp(Routes.SIGN_IN_SCREEN, Routes.SPLASH_SCREEN)
    }
}
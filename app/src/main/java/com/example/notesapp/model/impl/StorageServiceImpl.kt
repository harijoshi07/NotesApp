package com.example.notesapp.model.impl

import com.example.notesapp.model.Note
import com.example.notesapp.model.service.AccountService
import com.example.notesapp.model.service.StorageService
import com.google.firebase.Firebase
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(private val auth: AccountService) : StorageService {

    override val notes: Flow<List<Note>>
        get() = auth.currentUser.flatMapLatest { note ->
            Firebase.firestore
                .collection(NOTES_COLLECTION)
                .whereEqualTo(USER_ID_FIELD, note?.id)
                .dataObjects()
        }

    override suspend fun createNote(note: Note) {
        val noteWithUserId = note.copy(userId = auth.currentUserId)
        Firebase.firestore
            .collection(NOTES_COLLECTION)
            .add(noteWithUserId).await()
    }

    override suspend fun readNote(noteId: String): Note? {
        return Firebase.firestore
            .collection(NOTES_COLLECTION)
            .document(noteId).get().await().toObject()
    }

    override suspend fun updateNote(note: Note) {
        Firebase.firestore
            .collection(NOTES_COLLECTION)
            .document(note.id).set(note).await()
    }

    override suspend fun deleteNote(noteId: String) {
        Firebase.firestore
            .collection(NOTES_COLLECTION)
            .document(noteId).delete().await()
    }


    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val NOTES_COLLECTION = "notes"
    }
}
package com.example.firebasecrud.repository

import com.example.firebasecrud.model.Note
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    // Mereferensikan koleksi 'notes' di Firestore
    private val notesCollection = firestore.collection("notes")

    // CREATE: Menambahkan catatan baru
    suspend fun addNote(note: Note) {
        notesCollection.add(note).await()
    }

    // READ: Mengambil semua catatan
    fun getNotes() = notesCollection
        .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
        .snapshots() // Memberikan stream data real-time
        .map { snapshot ->
            snapshot.toObjects(Note::class.java).mapIndexed { index, note ->
                note.copy(id = snapshot.documents[index].id)
            }
        }

    // UPDATE: Memperbarui catatan yang ada
    suspend fun updateNote(note: Note) {
        notesCollection.document(note.id).set(note).await()
    }

    // DELETE: Menghapus catatan
    suspend fun deleteNote(noteId: String) {
        notesCollection.document(noteId).delete().await()
    }
}
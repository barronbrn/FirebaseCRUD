package com.example.firebasecrud.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasecrud.model.Note
import com.example.firebasecrud.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesState())
    val uiState = _uiState.asStateFlow()

    init {
        getNotes()
    }

    // READ (Real-time)
    private fun getNotes() {
        viewModelScope.launch {
            repository.getNotes()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { e -> _uiState.update { it.copy(error = e.message) } }
                .collect { notes ->
                    _uiState.update {
                        it.copy(isLoading = false, notes = notes)
                    }
                }
        }
    }

    // CREATE
    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.addNote(note)
        }
    }

    // UPDATE
    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    // DELETE
    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }
}
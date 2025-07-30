package com.example.firebasecrud.ui.screens

import com.example.firebasecrud.model.Note

data class NotesState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

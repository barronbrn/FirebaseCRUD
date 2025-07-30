package com.example.firebasecrud.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Note(
    var id: String = "",
    val title: String = "",
    val content: String = "",
    @ServerTimestamp
    val timestamp: Timestamp? = null
)

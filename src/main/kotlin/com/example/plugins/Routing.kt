package com.example.plugins

import com.example.data.repository.NotesRepository
import com.example.data.repository.NotesRepositoryImpl
import com.example.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val repository: NotesRepository = NotesRepositoryImpl()
    routing {
        getNotes(repository)
        saveNotes(repository)
        getNoteById(repository)
        deleteNoteById(repository)
        updateNote(repository)
        updateNotes(repository)
    }
}

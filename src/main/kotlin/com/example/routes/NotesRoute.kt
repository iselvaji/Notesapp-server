package com.example.routes

import com.example.data.model.Note
import com.example.data.repository.NotesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.getNotes(repository: NotesRepository) {
    get("/notes") {
        val notes = repository.getAllNotes()
        call.respond(HttpStatusCode.OK, notes)
    }
}

fun Route.updateNotes(repository: NotesRepository) {
    post("/updateNotes") {
        val request = call.receive<List<Note>>()
        println("Update request : $request")
        repository.upsertNote(request)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.saveNotes(repository: NotesRepository) {
    post("/saveNotes") {
        val request = call.receive<Note>()
        val addedNote = repository.addNotes(request)
        call.respond(HttpStatusCode.Created, addedNote)
    }
}

fun Route.getNoteById(repository: NotesRepository) {
    get("{id}") {
        val id = call.parameters["id"]?.toLong() ?: 0
        val note = repository.getNoteById(id)
        note?.let {
            call.respond(HttpStatusCode.Found, it)
        }?: call.respond(HttpStatusCode.NotFound,"Note not found with id $id")
    }
}

fun Route.deleteNoteById(repository: NotesRepository) {
    delete("{id}") {
        val id = call.parameters["id"]?.toLong() ?: 0
        val result = repository.deleteNote(id)
        call.respond(
            if (result) HttpStatusCode.OK else HttpStatusCode.NotFound,
            if (result) "Note with id $id deleted" else "Note with id $id not found"
        )
    }
}

fun Route.updateNote(repository: NotesRepository) {
    put("{id}") {
        val id = call.parameters["id"]?.toLong() ?: 0
        val note = call.receive<Note>()
        val result = repository.updateNote(note, id)
        call.respond(
            if (result) HttpStatusCode.OK else HttpStatusCode.NotFound,
            if (result) "Note with id $id updated" else "Note with id $id not found"
        )
    }
}
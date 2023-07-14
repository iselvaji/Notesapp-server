package com.example.data.repository

import com.example.data.model.Note

interface NotesRepository {
    fun addNotes(note: Note): Long
    fun getAllNotes(): List<Note>
    fun getNoteById(id: Long): Note?
    fun deleteNote(id: Long): Boolean
    fun updateNote(note: Note, id: Long): Boolean
    fun upsertNote(notes: List<Note>)
}
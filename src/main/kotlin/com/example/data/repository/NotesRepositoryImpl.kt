package com.example.data.repository

import com.example.data.database.DBHelper
import com.example.data.database.entities.NotesTable
import com.example.data.model.Note
import org.ktorm.database.asIterable
import org.ktorm.dsl.*
import org.ktorm.support.mysql.insertOrUpdate

class NotesRepositoryImpl : NotesRepository {

    private val database = DBHelper.database()

    override fun addNotes(note: Note): Long {
        return database.insertAndGenerateKey(NotesTable) {
            set(it.title, note.title)
            set(it.content, note.content)
            set(it.created, note.created)
        } as Long
    }

    override fun getAllNotes(): List<Note> {
        val dbNotes = database.from(NotesTable).select()
        return dbNotes.rowSet.asIterable().map { row ->
            Note(
                row[NotesTable.id] ?: -1,
                row[NotesTable.title] ?: "",
                row[NotesTable.content] ?: "",
                row[NotesTable.created] ?: 0L
            )
        }
    }

    override fun getNoteById(id: Long): Note? {
        val dbNotes = database.from(NotesTable).select().where { NotesTable.id eq id }
        val row = dbNotes.rowSet.asIterable().firstOrNull()
        return row?.let {
            Note(
                it[NotesTable.id] ?: -1,
                it[NotesTable.title] ?: "",
                it[NotesTable.content] ?: "",
                it[NotesTable.created] ?: 0L
            )
        }
    }

    override fun deleteNote(id: Long): Boolean {
        val affectedRow = database.delete(NotesTable) { NotesTable.id eq id }
        return affectedRow == 1
    }

    override fun updateNote(note: Note, id: Long): Boolean {
        val affectedRow = database.update(NotesTable) {
            set(it.title, note.title)
            set(it.content, note.content)
            set(it.created, note.created)
            where {
                it.id eq id
            }
        }
        return affectedRow == 1
    }

    override fun upsertNote(notes: List<Note>) {
        notes.forEach { note ->
            database.insertOrUpdate(NotesTable) {
                set(it.id, note.id)
                set(it.title, note.title)
                set(it.content, note.content)
                set(it.created, note.created)

                onDuplicateKey {
                    set(it.id, note.id)
                }
            }
        }
    }
}
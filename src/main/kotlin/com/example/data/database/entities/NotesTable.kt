package com.example.data.database.entities

import com.example.data.database.DBConstants
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

object NotesTable: Table<Nothing>(DBConstants.TABLE_NOTES) {
    val id = long("id").primaryKey()
    val title = varchar("title")
    val content = varchar("content")
    val created = long("created")
}

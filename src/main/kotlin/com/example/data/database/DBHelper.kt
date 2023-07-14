package com.example.data.database

import org.ktorm.database.Database

object DBHelper {
    fun database() = Database.connect(
        url = "jdbc:mysql://localhost:3306/notes_db",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "Pass@123"
    )
}
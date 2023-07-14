package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Long? = 0L,
    val title: String,
    val content: String,
    val created: Long,
)

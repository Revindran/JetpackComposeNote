package com.raveendran.composenote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "noteDB"
)

data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val desc: String?,
    val active: Boolean = false
)
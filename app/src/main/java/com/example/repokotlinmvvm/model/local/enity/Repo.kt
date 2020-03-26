package com.example.repokotlinmvvm.model.local.enity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo")
data class Repo(
    @ColumnInfo(name = "full_name")
    var fullName: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "stars")
    var stars: String,

    @ColumnInfo(name = "fork")
    var fork: String,

    @ColumnInfo(name = "language")
    var language: String

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int ?= null
}
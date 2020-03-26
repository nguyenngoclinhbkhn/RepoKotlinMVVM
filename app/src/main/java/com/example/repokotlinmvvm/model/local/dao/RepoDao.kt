package com.example.repokotlinmvvm.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.repokotlinmvvm.model.local.enity.Repo

@Dao
interface RepoDao {
    @Insert
    fun insertRepo(repo: Repo)

    @Delete
    fun deleteRepo(repo: Repo)

    @Query("SELECT * FROM repo")
    fun getAllRepo(): LiveData<List<Repo>>

    @Query("SELECT * FROM repo WHERE id = :idRepo")
    fun getRepoById(idRepo: Int) : LiveData<Repo>
}
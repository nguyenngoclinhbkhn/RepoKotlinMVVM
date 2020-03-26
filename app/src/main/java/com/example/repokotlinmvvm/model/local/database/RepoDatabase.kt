package com.example.repokotlinmvvm.model.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.repokotlinmvvm.model.local.dao.RepoDao
import com.example.repokotlinmvvm.model.local.enity.Repo

@Database(entities = [Repo::class],
    version = 1, exportSchema = false)
abstract class RepoDatabase: RoomDatabase() {

    abstract fun repoDao() : RepoDao
    companion object {
        var instance: RepoDatabase ?= null

        private val LOCK = Any()
        fun getInstance(context: Context): RepoDatabase{
            if (instance  == null){
                synchronized(RepoDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RepoDatabase::class.java, "repo_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }
    }

}
package com.example.repokotlinmvvm.model.respository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.repokotlinmvvm.model.local.dao.RepoDao
import com.example.repokotlinmvvm.model.local.database.RepoDatabase
import com.example.repokotlinmvvm.model.local.enity.Repo


class RepoRepository(private val application: Application) {
    private var allRepo: LiveData<List<Repo>>
    private lateinit var repo: LiveData<Repo>
    private val repoDatabase = RepoDatabase.getInstance(application)
    private lateinit var repoDao: RepoDao

    init {
        repoDao = repoDatabase.repoDao()
        allRepo = repoDao.getAllRepo()
    }


    fun insertRepo(repo: Repo) {
        InsertAsync(repoDao).execute(repo)
    }

    fun deleteRepo(repo: Repo) {
        DeleteAsyn(repoDao).execute(repo)
    }

    fun deleteAll() {
        DeleteAllAsyn(repoDao).execute()
    }

    fun getRepo(id: Int): LiveData<Repo> {
        repo = repoDao.getRepoById(id)
        return repo
    }

    fun getAllRepo(): LiveData<List<Repo>> {
        return allRepo
    }

    class DeleteAsyn(val repoDao: RepoDao) : AsyncTask<Repo, Void, String>() {
        override fun doInBackground(vararg params: Repo?): String {
            repoDao.deleteRepo(params[0]!!)
            return "ok"
        }

    }

    class DeleteAllAsyn(val repoDao: RepoDao) : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void): String {
            repoDao.deleteAll()
            return ""
        }

    }

    class InsertAsync(val repoDao: RepoDao) : AsyncTask<Repo, Void, String>() {
        override fun doInBackground(vararg params: Repo?): String? {
            val repo = params[0]
            repoDao.insertRepo(repo!!)
            return "ok"
        }


    }

}
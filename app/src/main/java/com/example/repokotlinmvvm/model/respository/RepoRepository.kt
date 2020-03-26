package com.example.repokotlinmvvm.model.respository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.repokotlinmvvm.model.local.dao.RepoDao
import com.example.repokotlinmvvm.model.local.database.RepoDatabase
import com.example.repokotlinmvvm.model.local.enity.Repo


class RepoRepository(private val application: Application){
    private var allRepo : LiveData<List<Repo>>
    private lateinit var repo: LiveData<Repo>
    private val repoDatabase = RepoDatabase.getInstance(application)
    private lateinit var repoDao: RepoDao
    init {
        repoDao = repoDatabase.repoDao()
        allRepo = repoDao.getAllRepo()
    }


    fun insertRepo(repo: Repo){
        InsertAsync(repoDao).execute(repo)
    }
    fun deleteRepo(repo: Repo){
        DeleteAsyn(repoDao).execute(repo)
    }
    fun getRepo(id: Int): LiveData<Repo>{
        repo = repoDao.getRepoById(id)
        return repo
    }
    fun getAllRepo(): LiveData<List<Repo>>{
        return allRepo
    }

    class DeleteAsyn(val repoDao: RepoDao) : AsyncTask<Repo, Void, Void>(){
        override fun doInBackground(vararg params: Repo?): Void {
            repoDao.deleteRepo(params[0]!!)
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
    class InsertAsync(val repoDao: RepoDao) : AsyncTask<Repo, Void, Void>() {
        override fun doInBackground(vararg params: Repo?): Void? {
            val repo = params[0]
            repoDao.insertRepo(repo!!)
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


    }

}
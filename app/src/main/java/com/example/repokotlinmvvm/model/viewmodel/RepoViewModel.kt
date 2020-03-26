package com.example.repokotlinmvvm.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.repokotlinmvvm.model.local.enity.Repo
import com.example.repokotlinmvvm.model.respository.RepoRepository

class RepoViewModel(application: Application) : AndroidViewModel(application) {
    private var repository = RepoRepository(application)
    private val allRepo = repository.getAllRepo()
    private lateinit var repo : Repo;

    fun getAllRepo(): LiveData<List<Repo>> {
        return allRepo
    }
    fun getRepo(id: Int): LiveData<Repo>{
        return repository.getRepo(id)
    }
    fun insertRepo(repo: Repo){
        repository.insertRepo(repo)
    }
    fun deleteRepo(repo: Repo){
        repository.deleteRepo(repo)
    }
}
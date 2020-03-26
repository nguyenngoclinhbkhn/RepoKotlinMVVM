package com.example.repokotlinmvvm.model.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.repokotlinmvvm.model.User
import com.example.repokotlinmvvm.model.respository.NetworkRepository

class NetworkViewModel(application: Application) : AndroidViewModel(application) {
    private val networkRepository = NetworkRepository()

    fun getUser(user: String, pass: String): LiveData<User>{
        return networkRepository.getUser(user, pass)
    }
    fun checkPass(pass: String): Boolean{
        if (pass.length == 6) {
            val arrayPass = pass.toCharArray()
            val char1 = arrayPass[0].toInt()
            for (i in 1 until arrayPass.size) {
                if (arrayPass[i].toInt() != char1) {
                    return true
                }
            }
            return false
        }
        return false
    }
}
package com.example.repokotlinmvvm.model.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.repokotlinmvvm.model.User
import com.example.repokotlinmvvm.model.remote.API
import com.example.repokotlinmvvm.model.remote.Client
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepository {
    private lateinit var client: API
    init {
        client = Client.createAPI()
    }
    companion object{
        private lateinit var networkRepository: NetworkRepository
        fun getNetworkRepository() : NetworkRepository{
            if (networkRepository == null){
                networkRepository = NetworkRepository()
            }
            return networkRepository
        }
    }
     fun getUser(userName: String, pass: String): LiveData<User> {
        var user: User?= null
         val liveData = MutableLiveData<User>()
         client.getUser(userName).enqueue(object : Callback<JsonElement>{
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                user = null
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                user = User(response.body()!!.asJsonObject.get("items").asJsonArray.get(0).asJsonObject.get("login").asString, pass)
                liveData.value = user
            }
        })
        return liveData
    }
}
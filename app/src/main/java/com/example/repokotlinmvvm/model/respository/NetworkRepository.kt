package com.example.repokotlinmvvm.model.respository

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.repokotlinmvvm.model.User
import com.example.repokotlinmvvm.model.local.enity.Repo
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

    companion object {
        private lateinit var networkRepository: NetworkRepository
        fun getNetworkRepository(): NetworkRepository {
            if (networkRepository == null) {
                networkRepository = NetworkRepository()
            }
            return networkRepository
        }
    }

    fun getUser(userName: String, pass: String): LiveData<User> {
        var user: User? = null
        val liveData = MutableLiveData<User>()
        client.getUser(userName).enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                user = null
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                user = User(
                    response.body()!!.asJsonObject.get("items").asJsonArray.get(0).asJsonObject.get(
                        "login"
                    ).asString, pass
                )
                liveData.value = user
            }
        })
        return liveData
    }

    fun getRepoSearch(nameRepo: String, sort: String, desc: String): LiveData<List<Repo>> {
        val liveData = MutableLiveData<List<Repo>>()
        var list = arrayListOf<Repo>()
        val call = when (sort) {
            "star" -> {
                client.getRepoStar(nameRepo, "star", "desc")
            }
            "updated" -> {
                client.getRepoStar(nameRepo, "updated", "desc")
            }
            else -> {
                client.getRepoStar(nameRepo, "", "")
            }
        }
        call.enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                try {
                    list.clear()
                    list = convertJsonToList(response.body()!!.asJsonObject)
                    liveData.value = list
                }catch (e: java.lang.Exception){
                    list.clear()
                    liveData.value = list
                    Log.e("TAG", "exeption ${e.toString()}")
                }

            }
        })
        return liveData
    }

    fun convertJsonToList(json: JsonElement): ArrayList<Repo> {
        val jsonArray = json.asJsonObject["items"].asJsonArray
        var list = arrayListOf<Repo>()
        for (i in 0 until jsonArray.size()) {
            var fullName = "Unknow"
            var des = "Unknow"
            var stars = "Unknow"
            var forks = "Unknow"

            try {
                fullName = jsonArray[i].asJsonObject["name"].asString
            } catch (e: Exception) {
            }
            try {
                des = jsonArray[i].asJsonObject["description"].asString
            } catch (e: Exception) {
            }
            try {
                stars = jsonArray[i].asJsonObject["stargazers_count"].asString
            } catch (e: Exception) {
            }
            try {
                forks = jsonArray[i].asJsonObject["forks_count"].asString
            } catch (e: Exception) {
            }

            var language: String? = "Unknown"
            try {
                language = jsonArray[i].asJsonObject["language"].asString
            } catch (e: Exception) {
            }
            val repo = Repo( fullName, des, stars, forks, language!!)
            list.add(repo)
        }
        return list
    }
}
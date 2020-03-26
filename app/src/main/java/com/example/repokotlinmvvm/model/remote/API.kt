package com.example.repokotlinmvvm.model.remote

import androidx.lifecycle.LiveData
import com.google.gson.JsonElement
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("users?")
    fun getUser(@Query("q") userName: String): Call<JsonElement>

    @GET("repositories?")
    fun getRepo(@Query("q") nameRepo: String): LiveData<JsonElement>

    @GET("repositories?")
    fun getRepoStar(@Query("q") nameRepo: String,
                    @Query("sort") star: String,
                    @Query("order") desc: String): Call<JsonElement>

    @GET("repositories?")
    fun getRepoUpdate(@Query("q") nameRepo: String,
                      @Query("sort") update: String,
                      @Query("order") desc: String): LiveData<JsonElement>

    @GET("repositories?")
    fun getRepoUpdateVer2(@Query("q") nameRepo: String,
                          @Query("sort") update: String,
                          @Query("order") desc: String): Call<JsonElement>
}
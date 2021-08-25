package com.example.myapplication.data.remote

import com.example.myapplication.data.model.ArticlesModel
import com.example.myapplication.data.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface APIService {

    @Headers("Accept: application/json")
    @GET("/v2/top-headlines?country=us&category=business&apiKey=59410da0b0ad472b8ceb3983dcda7fbe")
    suspend fun getEntity(
        @Header("Content-Type") contentType: String,
    ): ResponseModel

}
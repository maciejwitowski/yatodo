package com.example.yatodo.api

import okhttp3.ResponseBody
import retrofit2.http.GET

interface YatodoService {
    @GET("/")
    suspend fun main(): ResponseBody
}
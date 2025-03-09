package com.newsapp.lask.data.remote

import com.newsapp.lask.domain.model.LoginRequest
import com.newsapp.lask.domain.model.AuthRequest
import com.plcoding.jwtauthktorandroid.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("register")
    suspend fun signUp(
        @Body request: AuthRequest,
    )

    @POST("login")
    suspend fun signIn(
        @Body request: LoginRequest,
    ): TokenResponse

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String,
    )
}
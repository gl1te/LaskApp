package com.newsapp.lask.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.newsapp.lask.data.remote.AuthApi
import com.newsapp.lask.domain.model.LoginRequest
import com.newsapp.lask.domain.model.AuthResult
import com.newsapp.lask.domain.repository.AuthRepository
import com.newsapp.lask.domain.model.AuthRequest
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val prefs: SharedPreferences
): AuthRepository {

    override suspend fun signUp(username: String, email: String, password: String): AuthResult<Unit> {
        return try {
            api.signUp(
                request = AuthRequest(
                    username = username,
                    password = password,
                    email = email
                )
            )
            signIn(username, password)
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = LoginRequest(
                    username = username,
                    password = password
                )
            )
            prefs.edit()
                .putString("jwt", response.token)
                .apply()
            AuthResult.Authorized()
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch (e: HttpException) {
            Log.e("register", "HTTP error: code=${e.code()}, message=${e.message()}", e)
            if (e.code() == 401) AuthResult.Unauthorized() else AuthResult.UnknownError()
        } catch (e: Exception) {
            Log.e("register", "Exception: ${e.message}", e)
            AuthResult.UnknownError()
        }
    }
}
package com.newsapp.lask.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    suspend fun saveAppEntry()

    fun readAppEntry(): Flow<Boolean>

    suspend fun saveLoginAppEntry()

    fun readLoginAppEntry(): Flow<Boolean>


}
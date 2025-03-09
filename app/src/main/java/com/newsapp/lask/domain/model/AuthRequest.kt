package com.newsapp.lask.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val username: String,
    val email: String,
    val password: String,
)

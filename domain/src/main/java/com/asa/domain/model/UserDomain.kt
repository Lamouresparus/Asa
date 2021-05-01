package com.asa.domain.model

data class UserDomain(
        val userId: String,
        val email: String,
        val location: String,
        val userPhoto: String,
        val gender: String
)
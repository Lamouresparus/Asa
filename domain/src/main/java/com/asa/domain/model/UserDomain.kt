package com.asa.domain.model

data class UserDomain(
    val userId: String,
    val email: String,
    val userType: Int,
    val isRegistrationComplete: Boolean = false,
    val regNumber: String? = null,
    val staffId: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val level: Int? = null,
    val imageUrl: String? = null,
    val userBio: String? = null,
    val school: String? = null,
    val department: String? = null
) {
    constructor() : this("", "", -1, false)
}
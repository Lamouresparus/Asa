package com.asa.domain.model

data class UserDomain(
        val userId: String,
        val email: String,
        val userType: Int,
        val isRegistrationComplete: Boolean = false,
        val regNumber: String? = null,
        val staffId: String? = null
) {
    constructor() : this("", "", -1, false)
}
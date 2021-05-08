package com.asa.domain.model

data class UserDomain(
        val userId: String,
        val email: String,
        val userType: Int,
        val regNumber: String? = null,
        val staffId: String? = null
) {
    constructor() : this("", "", -1, "", "")
}
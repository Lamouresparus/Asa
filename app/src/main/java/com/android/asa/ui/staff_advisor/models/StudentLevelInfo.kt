package com.android.asa.ui.staff_advisor.models

import com.asa.domain.model.UserDomain
import java.io.Serializable

data class StudentLevelInfo(
    val id: Int,
    val level: String,
    val levelTag: String,
    var numberOfStudents: Int = 0,
    var students: List<UserDomain> = emptyList()
) : Serializable
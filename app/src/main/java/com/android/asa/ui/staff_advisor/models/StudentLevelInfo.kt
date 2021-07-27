package com.android.asa.ui.staff_advisor.models

data class StudentLevelInfo(
    val id: Int,
    val level: String,
    val levelTag: String,
    var numberOfStudents: Int = 0
)
package com.asa.domain.model

data class SemesterDomain(
    val hasSemesterBegun: Boolean = false,
    val noOfCoursesOffered: Int = 0,
    val totalCreditUnit: Int = 0
)
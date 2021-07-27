package com.asa.domain.model

import java.io.Serializable

data class CourseTotalReadingHoursDomain(
    val course: String,
    val totalReadHours: Double
) : Serializable
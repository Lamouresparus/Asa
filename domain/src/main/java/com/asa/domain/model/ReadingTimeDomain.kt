package com.asa.domain.model

data class ReadingTimeDomain(
    val day: String,
    val courseCode: String,
    val startTime: Int,
    val endTime: Int,
    val totalReadingHours: Double = 0.0
) {
    constructor() : this("", "", 0, 0)
}
package com.asa.domain.model

data class ReadingTimetableDomain(
    val day: String,
    val courses: List<ReadingTimeDomain>
)
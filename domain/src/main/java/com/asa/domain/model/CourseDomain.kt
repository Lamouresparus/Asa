package com.asa.domain.model

class CourseDomain(
        val title: String,
        val courseCode: String,
        val creditUnit: Int,
        val description: String,
        val lecturer: String,
        val lectureDays: List<LectureDayDomain>
)

data class LectureDayDomain(
        val courseId: String,
        val dayOfWeek: String,
        val venue: String,
        val startTime: Long,
        val endTime: Long)

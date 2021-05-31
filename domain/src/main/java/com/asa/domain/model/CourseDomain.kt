package com.asa.domain.model

class CourseDomain(
        val title: String,
        val courseCode: String,
        val creditUnit: Int,
        val description: String,
        val lecturer: String,
        val lectureDayOfWeek: List<String>,
        val lectureDays: List<LectureDayDomain>
) {
    constructor() : this("", "", 0, "", "", emptyList(), emptyList())
}

data class LectureDayDomain(
        var courseId: String = "",
        var dayOfWeek: String = "",
        var venue: String = "",
        var startTime: String = "",
        var endTime: String = "")

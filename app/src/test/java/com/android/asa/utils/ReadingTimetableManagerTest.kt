package com.android.asa.utils

import com.asa.domain.model.CourseDomain
import com.asa.domain.model.LectureDayDomain
import com.asa.domain.ReadingTimetableManager
import org.junit.Assert.*
import org.junit.Test

class ReadingTimetableManagerTest {

    val sut = ReadingTimetableManager()
    val dayOfTheWeek = arrayListOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday"
    )

    val courses = listOf(
        CourseDomain("Computer Appreciation", "CPE211", 3, "hmm test", "Mr O", listOf(dayOfTheWeek[0], dayOfTheWeek[1]), listOf(LectureDayDomain())),
        CourseDomain("Computer Hardware", "CPE212", 2, "hmm test", "Mrs O", listOf(dayOfTheWeek[2], dayOfTheWeek[3]), listOf(LectureDayDomain())),
        CourseDomain("Computer Software", "CPE213", 3, "hmm test", "Mrs Emem", listOf(dayOfTheWeek[4], dayOfTheWeek[0]), listOf(LectureDayDomain())),
        CourseDomain("Engine Maths", "CPE214", 4, "hmm test", "Mr Paul", listOf(dayOfTheWeek[1]), listOf(LectureDayDomain())),
        CourseDomain("Computer Repairs", "CPE215", 1, "hmm test", "Mr O", listOf(dayOfTheWeek[3], dayOfTheWeek[4]), listOf(LectureDayDomain())),
        CourseDomain("Computer Lab Maint", "CPE216", 3, "hmm test", "Mr O", listOf(dayOfTheWeek[0]), listOf(LectureDayDomain())),
        CourseDomain("Computer Appreciation", "CPE217", 3, "hmm test", "Mr O", listOf(dayOfTheWeek[3]), listOf(LectureDayDomain())),
        CourseDomain("Computer Appreciation", "CPE218", 3, "hmm test", "Mr O", listOf(dayOfTheWeek[0]), listOf(LectureDayDomain())),
        CourseDomain("Computer Appreciation", "CPE219", 1, "hmm test", "Mr O", listOf(dayOfTheWeek[1]), listOf(LectureDayDomain()))
    )

    @Test
    fun `test time table generation`() {

        val result = sut.generateReadingTimeTable(courses, 0, 0, 0)


        assertEquals(1, result.toString())
    }
}
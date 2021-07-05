package com.android.asa.utils

import com.asa.domain.model.CourseDomain
import com.asa.domain.model.ReadingTimeDomain

class ReadingTimetableManager {

    private val dayOfTheWeek = arrayListOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday"
    )

    fun generateReadingTimeTable(courses: List<CourseDomain>?, prefReadDay: Int, prefReadTime : Int, avrgReadHours: Int ): List<ReadingTimeDomain> {
        val coursesOffered = courses ?: listOf()
        val readingTimeTable = mutableListOf<ReadingTimeDomain>()
        val maxReadHours = if (avrgReadHours == 0) {
            2
        } else {
            4
        }

        //weekends
        if (prefReadDay == 0) {
            var satReadStartTime: Int
            var sunReadStartTime: Int
            val x = coursesOffered.size % 2
            val satCourseNumber = coursesOffered.size / 2 + x

            //night
            if (prefReadTime == 0) {
                satReadStartTime = 20
                sunReadStartTime = 20

                //saturday
                for (i in 0 until satCourseNumber) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[5], coursesOffered[i].courseCode, satReadStartTime, (satReadStartTime + 1)%24)
                    readingTimeTable.add(readingTime)
                    satReadStartTime += 1

                    //giving a break of 1 hour after 2 hours of reading
                    if (satReadStartTime % maxReadHours == 0) {
                        satReadStartTime += 1
                    }

                    if (satReadStartTime > 23) {
                        satReadStartTime %= 24
                    }
                }

                //sunday
                for (i in satCourseNumber until coursesOffered.size) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[6], coursesOffered[i].courseCode, sunReadStartTime, (sunReadStartTime + 1)%24)
                    readingTimeTable.add(readingTime)
                    sunReadStartTime += 1

                    //giving a break maxReadHours of reading
                    if (sunReadStartTime % maxReadHours == 0) {
                        sunReadStartTime += 1
                    }

                    if (sunReadStartTime > 23) {
                        sunReadStartTime %= 24
                    }
                }
            }

            //morning
            if (prefReadTime == 1) {

                satReadStartTime = 8
                sunReadStartTime = 12

                //saturday
                for (i in 0 until satCourseNumber) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[5], coursesOffered[i].courseCode, satReadStartTime, satReadStartTime + 1)
                    readingTimeTable.add(readingTime)
                    satReadStartTime += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (satReadStartTime % maxReadHours == 0) {
                        satReadStartTime += 1
                    }
                }

                //sunday
                for (i in satCourseNumber until coursesOffered.size) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[6], coursesOffered[i].courseCode, sunReadStartTime, sunReadStartTime + 1)
                    readingTimeTable.add(readingTime)
                    sunReadStartTime += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (sunReadStartTime % maxReadHours == 0) {
                        sunReadStartTime += 1
                    }
                }
            }
        }

        //weekdays
        if (prefReadDay == 1) {

            val mondayCourses = coursesOffered.asSequence().filter { it.lectureDayOfWeek.contains(dayOfTheWeek[0]) }
            val tuesdayCourses: Sequence<CourseDomain> = coursesOffered.asSequence().filter { it.lectureDayOfWeek.contains(dayOfTheWeek[1]) }
            val wednesdayCourses = coursesOffered.asSequence().filter { it.lectureDayOfWeek.contains(dayOfTheWeek[2]) }
            val thursdayCourses = coursesOffered.asSequence().filter { it.lectureDayOfWeek.contains(dayOfTheWeek[3]) }
            val fridayCourses = coursesOffered.asSequence().filter { it.lectureDayOfWeek.contains(dayOfTheWeek[4]) }

            //night
            if (prefReadTime == 0) {
                val dailyReadStartTime = 20

                //monday
                var mondayReadStartTime = dailyReadStartTime
                for (i in 0 until mondayCourses.count()) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[0], mondayCourses.elementAt(i).courseCode, mondayReadStartTime, (mondayReadStartTime + 1)%24)
                    readingTimeTable.add(readingTime)
                    mondayReadStartTime += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (mondayReadStartTime % maxReadHours == 0) {
                        mondayReadStartTime += 1
                    }

                    if (mondayReadStartTime > 23) {
                        mondayReadStartTime %= 24
                    }
                }

                //tuesday
                var tuesdayReadStartTime = dailyReadStartTime
                for (i in 0 until tuesdayCourses.count()) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[1], tuesdayCourses.elementAt(i).courseCode, tuesdayReadStartTime, (tuesdayReadStartTime + 1)%24)
                    readingTimeTable.add(readingTime)
                    tuesdayReadStartTime += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (tuesdayReadStartTime % maxReadHours == 0) {
                        tuesdayReadStartTime += 1
                    }

                    if (tuesdayReadStartTime > 23) {
                        tuesdayReadStartTime %= 24
                    }
                }

                //wednesday
                var wednesdayReadStartTime = dailyReadStartTime
                for (i in 0 until wednesdayCourses.count()) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[2], wednesdayCourses.elementAt(i).courseCode, wednesdayReadStartTime, (wednesdayReadStartTime + 1)%24)
                    readingTimeTable.add(readingTime)
                    wednesdayReadStartTime += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (wednesdayReadStartTime % maxReadHours == 0) {
                        wednesdayReadStartTime += 1
                    }

                    if (wednesdayReadStartTime > 23) {
                        wednesdayReadStartTime %= 24
                    }
                }

                //thursday
                var thursdayReadStartTime = dailyReadStartTime
                for (i in 0 until thursdayCourses.count()) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[3], thursdayCourses.elementAt(i).courseCode, thursdayReadStartTime, (thursdayReadStartTime + 1)%24)
                    readingTimeTable.add(readingTime)
                    thursdayReadStartTime += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (thursdayReadStartTime % maxReadHours == 0) {
                        thursdayReadStartTime += 1
                    }

                    if (thursdayReadStartTime > 23) {
                        thursdayReadStartTime %= 24
                    }
                }

                //friday
                var fridayReadStartTime = dailyReadStartTime
                for (i in 0 until fridayCourses.count()) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[4], fridayCourses.elementAt(i).courseCode, fridayReadStartTime, (fridayReadStartTime + 1)%24)
                    readingTimeTable.add(readingTime)
                    fridayReadStartTime += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (fridayReadStartTime % maxReadHours == 0) {
                        fridayReadStartTime += 1
                    }

                    if (fridayReadStartTime > 23) {
                        fridayReadStartTime %= 24
                    }
                }
            }

            //day
            if (prefReadTime == 1) {
                val dailyReadEndTime = 6
                var numberOfHoursRead = 0

                //monday
                var mondayReadEndTime = dailyReadEndTime
                for (i in 0 until mondayCourses.count()) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[0], mondayCourses.elementAt(i).courseCode, mondayReadEndTime - 1, mondayReadEndTime)
                    readingTimeTable.add(readingTime)
                    mondayReadEndTime -= 1
                    numberOfHoursRead += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (numberOfHoursRead == maxReadHours) {
                        mondayReadEndTime -= 1
                        numberOfHoursRead = 0
                    }
                }

                //tuesday
                numberOfHoursRead = 0
                var tuesdayReadEndTime = dailyReadEndTime
                for (i in 0 until tuesdayCourses.count()) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[1], tuesdayCourses.elementAt(i).courseCode, tuesdayReadEndTime - 1, tuesdayReadEndTime)
                    readingTimeTable.add(readingTime)
                    tuesdayReadEndTime -= 1
                    numberOfHoursRead += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (numberOfHoursRead == maxReadHours) {
                        tuesdayReadEndTime -= 1
                        numberOfHoursRead = 0
                    }
                }

                //wednesday
                numberOfHoursRead = 0
                var wednesdayReadEndTime = dailyReadEndTime
                for (i in 0 until wednesdayCourses.count()) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[2], wednesdayCourses.elementAt(i).courseCode, wednesdayReadEndTime - 1, wednesdayReadEndTime)
                    readingTimeTable.add(readingTime)
                    wednesdayReadEndTime -= 1
                    numberOfHoursRead += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (numberOfHoursRead == maxReadHours) {
                        wednesdayReadEndTime -= 1
                        numberOfHoursRead = 0
                    }
                }

                //thursday
                numberOfHoursRead = 0
                var thursdayReadEndTime = dailyReadEndTime
                for (i in 0 until thursdayCourses.count()) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[3], thursdayCourses.elementAt(i).courseCode, thursdayReadEndTime - 1, thursdayReadEndTime)
                    readingTimeTable.add(readingTime)
                    thursdayReadEndTime -= 1
                    numberOfHoursRead += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (numberOfHoursRead == maxReadHours) {
                        thursdayReadEndTime -= 1
                        numberOfHoursRead = 0
                    }
                }

                //friday
                numberOfHoursRead = 0
                var fridayReadEndTime = dailyReadEndTime
                for (i in 0 until fridayCourses.count()) {
                    val readingTime = ReadingTimeDomain(dayOfTheWeek[4], fridayCourses.elementAt(i).courseCode, fridayReadEndTime - 1, fridayReadEndTime)
                    readingTimeTable.add(readingTime)
                    fridayReadEndTime -= 1
                    numberOfHoursRead += 1

                    //giving a break of 1 hour after maxReadHours of reading
                    if (numberOfHoursRead == maxReadHours) {
                        fridayReadEndTime -= 1
                        numberOfHoursRead = 0
                    }
                }
            }
        }

        return readingTimeTable
    }
}
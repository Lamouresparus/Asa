package com.android.asa.ui.home


import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.asa.domain.model.CourseDomain
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class TodaysClassesAdapter(private val courses: MutableList<CourseDomain>) : RecyclerView.Adapter<TodaysClassesAdapter.ViewHolder>() {

    private val colors: List<String> = listOf("#00BBBA", "#72ED77", "#FFAD00", "#EB5757", "#BB6BD9", "#4F4F4F")



    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.todays_classes_itemview, parent, false)) {


        var progressIndicator: CircularProgressIndicator = itemView.findViewById(R.id.attended_classes_progress)
        var progressText: TextView = itemView.findViewById(R.id.progress_text)
        private var courseTitle: TextView = itemView.findViewById(R.id.course_title)
        private var classTime: TextView = itemView.findViewById(R.id.class_time)
        private var classVenue: TextView = itemView.findViewById(R.id.class_venue)
        private var lecturer: TextView = itemView.findViewById(R.id.lecturers_name)
        private var card: CardView = itemView.findViewById(R.id.todays_classes_card)


        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(course: CourseDomain, color: String) {
            card.setCardBackgroundColor(Color.parseColor(color))
            val day = LocalDate.now().dayOfWeek.name.toLowerCase(Locale.ROOT)
            val lecture = course.lectureDays.filter { it.dayOfWeek == day.capitalize()}


            classVenue.text = lecture[0].venue

            lecturer.text= course.lecturer

            var courseCode = course.courseCode.toUpperCase(Locale.ROOT)
            val courseStartTime = splitTime(lecture[0].startTime)
            val courseEndTime = splitTime(lecture[0].endTime)
            var meridian = "AM"
            courseCode = courseCode.substring(0,3)+" "+courseCode.substring(3, 6)
            courseTitle.text = courseCode

            if(courseStartTime[0]>12 || courseEndTime[0]>12){
                courseStartTime[0] -= 12
                courseEndTime [0]-= 12
                meridian = "PM"

            }
            val courseStartTimeHr = courseStartTime[0]
            val courseStartTimeMin = courseStartTime[1]
            val courseEndTimeHr = courseEndTime[0]
            val courseEndTimeMin = courseEndTime[1]

            "$courseStartTimeHr:$courseStartTimeMin-$courseEndTimeHr:$courseEndTimeMin $meridian".also { classTime.text = it }


        }

        private fun splitTime(time: String): ArrayList<Int> {
            val arr = time.split(":").toTypedArray()
            val arrInt : ArrayList<Int> = arrayListOf()
            arr.forEach { time: String ->
                arrInt.add(time.toInt())
            }
            return arrInt

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position]
        var colorPosition = position
        if(colorPosition>colors.size-1){
            colorPosition =  (position-1)%(colors.size-1)

        }
        val courseColor = colors[colorPosition]

        holder.bind(course, courseColor)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

}

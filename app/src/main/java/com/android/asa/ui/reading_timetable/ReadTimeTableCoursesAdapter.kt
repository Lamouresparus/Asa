package com.android.asa.ui.reading_timetable

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.android.asa.utils.ColorUtils
import com.asa.domain.model.CourseTotalReadingHoursDomain
import com.google.android.material.progressindicator.CircularProgressIndicator

class ReadTimeTableCoursesAdapter(
    private val totalReadingHours: List<CourseTotalReadingHoursDomain>,
    private val onClick: ((CourseTotalReadingHoursDomain) -> Unit)? = null
) : RecyclerView.Adapter<ReadTimeTableCoursesAdapter.ViewHolder>() {

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.reading_timetable_courses_item_view, parent, false)) {
        val cardView: CardView = itemView.findViewById(R.id.reading_courses_card)
        val courseCode: TextView = itemView.findViewById(R.id.course_code)
        val courseProgress: TextView = itemView.findViewById(R.id.progress_text)
        val readingProgress: CircularProgressIndicator = itemView.findViewById(R.id.reading_progress)

        fun bind(courseReadingHour: CourseTotalReadingHoursDomain, color: String) {
            cardView.setCardBackgroundColor(Color.parseColor(color))
            val course = courseReadingHour.course.substring(0, 3) + " " + courseReadingHour.course.substring(3, 6)
            courseCode.text = course
            courseProgress.text = courseReadingHour.totalReadHours.toInt().toString().plus("hrs")
            readingProgress.progress = (courseReadingHour.totalReadHours * 10).toInt()
            cardView.setOnClickListener {
                onClick?.invoke(courseReadingHour)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val courseReadingHour = totalReadingHours[position]
        val courseColor = ColorUtils.getCourseCardColor(position)
        holder.bind(courseReadingHour, courseColor)
    }

    override fun getItemCount(): Int {
        return totalReadingHours.size
    }
}
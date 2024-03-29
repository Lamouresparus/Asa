package com.android.asa.ui.reading_timetable

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.android.asa.utils.ColorUtils
import com.asa.domain.model.ReadingTimeDomain

class DailyCoursesAdapter(private val courses: List<ReadingTimeDomain>) : RecyclerView.Adapter<DailyCoursesAdapter.ViewHolder>() {

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.reading_timetable_daily_courses_item_view, parent, false)) {
        private val courseCode: TextView = itemView.findViewById(R.id.course_code)
        private val readTime: TextView = itemView.findViewById(R.id.course_time)
        private val cardView: CardView = itemView.findViewById(R.id.daily_courses_card)

        fun bind(readingTimeDomain: ReadingTimeDomain) {
            courseCode.text = readingTimeDomain.courseCode
            readTime.text = convertTime(readingTimeDomain.startTime).plus("-").plus(convertTime(readingTimeDomain.endTime))
            cardView.setCardBackgroundColor(Color.parseColor(ColorUtils.getCourseCardColor(absoluteAdapterPosition)))
        }

        private fun convertTime(time: Int): String {
            var timeString = time.toString()
            var meridian = "AM"
            if (time >= 12) {
                meridian = "PM"
                if (time > 12) {
                    timeString = (time % 12).toString()
                }
            }
            return timeString.plus(meridian)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val readingTimeDomain = courses[position]
        holder.bind(readingTimeDomain)
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}
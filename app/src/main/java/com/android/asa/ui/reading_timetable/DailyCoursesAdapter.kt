package com.android.asa.ui.reading_timetable

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.asa.domain.model.ReadingTimeDomain

class DailyCoursesAdapter(private val courses: List<ReadingTimeDomain>) : RecyclerView.Adapter<DailyCoursesAdapter.ViewHolder>() {
    private val colors: List<String> = listOf("#00BBBA", "#FFAD00", "#EB5757", "#72ED77", "#4F4F4F", "#BB6BD9", "#4F4F4F")

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.reading_timetable_daily_courses_item_view, parent, false)) {
        private val courseCode: TextView = itemView.findViewById(R.id.course_code)
        private val readTime: TextView = itemView.findViewById(R.id.course_time)
        private val cardView: CardView = itemView.findViewById(R.id.daily_courses_card)

        fun bind(readingTimeDomain: ReadingTimeDomain) {
            courseCode.text = readingTimeDomain.courseCode
            readTime.text = convertTime(readingTimeDomain.startTime).plus("-").plus(convertTime(readingTimeDomain.endTime))
            cardView.setCardBackgroundColor(Color.parseColor(getBackgroundColor(colorPosition)))
            colorPosition++
        }

        private fun convertTime(time: Int): String {
            var timeString = time.toString()
            if (time >= 12) {

                if (time > 12) {
                    timeString = (time % 12).toString()
                }
                timeString.plus("PM")
            } else {
                timeString.plus("AM")
            }
            return timeString
        }

        private fun getBackgroundColor(number: Int) :String {
            var index = number
            if (index > colors.size - 1) {
                index %= (colors.size - 1)
            }
            return colors[index]
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

    companion object {
        private var colorPosition = 0
    }
}
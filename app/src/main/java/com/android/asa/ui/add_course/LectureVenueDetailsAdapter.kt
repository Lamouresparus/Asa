package com.android.asa.ui.add_course

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R

class LectureVenueDetailsAdapter(private val lectureDays: ArrayList<String>, private val lectureDayListener: LectureDayListener) : RecyclerView.Adapter<LectureVenueDetailsAdapter.ViewHolder>() {


    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.add_course_details_itemview, parent, false)) {
        private var venue: TextView = itemView.findViewById(R.id.venue)
        private val lectureDay: TextView = itemView.findViewById(R.id.lecture_day)
        var startTime: TextView = itemView.findViewById(R.id.start_time)
        var endTime: TextView = itemView.findViewById(R.id.end_time)

        fun bind(day: String) {
            lectureDay.text = day
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day: String = lectureDays[position]
        holder.bind(day)
        holder.endTime.setOnClickListener {
            lectureDayListener.setLectureTime(holder.endTime, "Lecture ends:")
        }

        holder.startTime.setOnClickListener {
            lectureDayListener.setLectureTime(holder.startTime, "Lecture begins:")

        }
    }

    override fun getItemCount(): Int {
        return lectureDays.size
    }
}
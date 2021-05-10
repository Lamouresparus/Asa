package com.android.asa.ui.add_course

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.android.asa.extensions.showTimePicker
import com.asa.domain.model.LectureDayDomain

class LectureVenueDetailsAdapter(private val lectureDays: MutableList<LectureDayDomain>, private val lectureDayListener: LectureDayListener) : RecyclerView.Adapter<LectureVenueDetailsAdapter.ViewHolder>() {


    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.add_course_details_itemview, parent, false)) {
        var venue: TextView = itemView.findViewById(R.id.venue)
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day: LectureDayDomain = lectureDays[position]
        holder.bind(day.dayOfWeek)
        holder.endTime.setOnClickListener {
            it.showTimePicker("Lecture ends:") { endTime ->
                holder.endTime.text = endTime
                day.endTime = endTime
            }
        }

        holder.startTime.setOnClickListener {
            it.showTimePicker("Lecture ends:") { startTime ->
                holder.startTime.text = startTime
                day.startTime = startTime
            }
        }

        holder.venue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                day.venue = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    override fun getItemCount(): Int {
        return lectureDays.size
    }

}
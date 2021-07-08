package com.android.asa.ui.reading_timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.asa.domain.model.ReadingTimetableDomain

class ReadingTimetableAdapter(private val readingTimetable: MutableList<ReadingTimetableDomain>) : RecyclerView.Adapter<ReadingTimetableAdapter.ViewHolder>() {

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.reading_timetable_item_view, parent, false)) {

        private val day: TextView = itemView.findViewById(R.id.read_day)
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.reading_timetable_daily_courses_item_view)

        fun bind(readingTimetable: ReadingTimetableDomain) {
            day.text = readingTimetable.day.subSequence(0,3 )
            with(recyclerView) {
                adapter = DailyCoursesAdapter(readingTimetable.courses)
                layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(readingTimetable[position])
    }

    override fun getItemCount(): Int {
        return readingTimetable.size
    }
}
package com.android.asa.ui.reading_progress

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.asa.domain.model.DailyTotalReadingHoursDomain
import kotlin.math.roundToInt

class DailyReadingChartAdapter(private val dailyReadingHours: List<DailyTotalReadingHoursDomain>) : RecyclerView.Adapter<DailyReadingChartAdapter.ViewHolder>() {

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.daily_reading_chart_item_view, parent, false)) {
        val hoursProgress: ProgressBar = itemView.findViewById(R.id.progress_bar_hours)
        val day: TextView = itemView.findViewById(R.id.read_day)

        fun bind(dailyReadHour: DailyTotalReadingHoursDomain) {
            val hours = dailyReadHour.totalReadHours.roundToInt() * 10
            hoursProgress.progress = hours
            day.text = dailyReadHour.day.subSequence(0, 3)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dailyReadHours = dailyReadingHours[position]
        holder.bind(dailyReadHours)
    }

    override fun getItemCount(): Int {
        return dailyReadingHours.size
    }
}


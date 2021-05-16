package com.android.asa.ui.home


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.asa.domain.model.CourseDomain
import com.google.android.material.progressindicator.CircularProgressIndicator

class TodaysClassesAdapter(private val courses: MutableList<CourseDomain>) : RecyclerView.Adapter<TodaysClassesAdapter.ViewHolder>() {

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.todays_classes_itemview, parent, false)) {
        var progressIndicator: CircularProgressIndicator = itemView.findViewById(R.id.attended_classes_progress)
        var progressText: TextView = itemView.findViewById(R.id.progress_text)
        var courseTitle: TextView = itemView.findViewById(R.id.course_title)
        var classTime: TextView = itemView.findViewById(R.id.class_time)
        var classVenue: TextView = itemView.findViewById(R.id.class_venue)
        var lecturer: TextView = itemView.findViewById(R.id.lecturers_name)


        fun bind(course: CourseDomain) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}

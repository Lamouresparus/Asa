package com.android.asa.ui.add_course

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.asa.domain.model.CourseDomain
import java.util.*

class AllCoursesAdapter() : RecyclerView.Adapter<AllCoursesAdapter.ViewHolder>() {

    private var addedCourses = listOf<CourseDomain>()


    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.added_courses_item_view, parent, false)) {
        private var courseCodeTv: TextView = itemView.findViewById(R.id.course_name_tv)
        private var creditLoadTv: TextView = itemView.findViewById(R.id.credit_load_tv)

        fun bind(course: CourseDomain) {
            var courseCode = course.courseCode.toUpperCase(Locale.ROOT)
            courseCode = courseCode.substring(0,3)+" "+courseCode.substring(3, 6)
            courseCodeTv.text = courseCode
            Log.d("course", course.courseCode)
            val creditLoad = course.creditUnit.toString()
            Log.d("course", creditLoad)

            creditLoadTv.text = "CL: $creditLoad"

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course: CourseDomain = addedCourses[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int {
        return addedCourses.size
    }

    fun setCourses(courses: List<CourseDomain>){
        addedCourses = courses
        notifyDataSetChanged()
    }

}
package com.android.asa.ui.staff_advisor

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.android.asa.ui.staff_advisor.models.StudentLevelInfo
import com.android.asa.utils.ColorUtils

class StudentLevelAdapter constructor(
    private val levelsInfo: List<StudentLevelInfo>
) : RecyclerView.Adapter<StudentLevelAdapter.ViewHolder>() {

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.student_level_item_view, parent, false)) {
        private val levelTv: TextView = itemView.findViewById(R.id.student_level_tv)
        private val levelTagTv: TextView = itemView.findViewById(R.id.level_tag_tv)
        private val cardView: CardView = itemView.findViewById(R.id.student_level_card)
        private val noOfStudentsTv: TextView = itemView.findViewById(R.id.number_of_students)
        fun bind(studentLevelInfo: StudentLevelInfo, color: String) {
            levelTv.text = studentLevelInfo.level
            levelTagTv.text = studentLevelInfo.levelTag
            noOfStudentsTv.text = String.format("%s Students(s)", studentLevelInfo.numberOfStudents)
            cardView.setCardBackgroundColor(Color.parseColor(color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = ColorUtils.getCourseCardColor(position)
        holder.bind(levelsInfo[position], color)
    }

    override fun getItemCount(): Int {
        return levelsInfo.size
    }
}
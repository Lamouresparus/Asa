package com.android.asa.ui.staff_advisor

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R

class StudentLevelAdapter : RecyclerView.Adapter<StudentLevelAdapter.ViewHolder>() {
    private val colors: List<String> = listOf("#00BBBA", "#72ED77", "#FFAD00", "#EB5757", "#BB6BD9", "#4F4F4F")
    private val levels: List<String> = listOf("100 Level", "200 Level", "300 Level", "400 Level", "500 Level", "600 Level", "700 Level")
    private val levelTags: List<String> = listOf("Freshmen", "Sophomore", "Junior", "Senior", "Final Year", "Extra Year 1", "Extra Year 2")

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.student_level_item_view, parent, false)) {
        private val levelTv: TextView = itemView.findViewById(R.id.student_level_tv)
        private val levelTagTv: TextView = itemView.findViewById(R.id.level_tag_tv)
        private val cardView: CardView = itemView.findViewById(R.id.student_level_card)
        fun bind(level: String, levelTag: String, color: String) {
            levelTv.text = level
            levelTagTv.text = levelTag
            cardView.setCardBackgroundColor(Color.parseColor(color))

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val level = levels[position]
        val levelTag = levelTags[position]
        var colorPosition = position
        if (colorPosition > colors.size - 1) {
            colorPosition = (position - 1) % (colors.size - 1)

        }
        val color = colors[colorPosition]

        holder.bind(level, levelTag, color)
    }

    override fun getItemCount(): Int {
        return levels.size
    }
}
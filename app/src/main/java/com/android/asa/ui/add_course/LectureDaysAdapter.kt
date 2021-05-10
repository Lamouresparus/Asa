package com.android.asa.ui.add_course

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.asa.domain.model.LectureDayDomain

class LectureDaysAdapter(private val lectureDayListener: LectureDayListener) : RecyclerView.Adapter<LectureDaysAdapter.ViewHolder>() {

    private val lectureDays: ArrayList<String> = arrayListOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")


    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.select_lecture_day_itemview, parent, false)) {
        private var checkedTv: CheckedTextView = itemView.findViewById(R.id.lecture_day)

        fun bind(day: String) {
            checkedTv.text = day[0].toString()

            checkedTv.setOnClickListener {
                if (checkedTv.isChecked) {
                    lectureDayListener.isUnchecked(dayOfWeek = day)
                    checkedTv.setTextColor(Color.parseColor("#838383"))
                    checkedTv.setBackgroundResource(R.drawable.ic_circle_grey)

                    checkedTv.isChecked = false

                } else {
                    lectureDayListener.isChecked(LectureDayDomain(dayOfWeek = day))
                    checkedTv.setTextColor(Color.parseColor("#FC8F2F"))
                    checkedTv.setBackgroundResource(R.drawable.ic_circle_color_accent)
                    checkedTv.isChecked = true


                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day: String = lectureDays[position]
        holder.bind(day)
    }

    override fun getItemCount(): Int {
        return lectureDays.size
    }
}
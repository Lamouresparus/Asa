package com.android.asa.ui.staff_advisor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.R
import com.android.asa.databinding.LayoutForStudentItemViewBinding
import com.android.asa.extensions.loadImage
import com.asa.domain.model.UserDomain

class StudentListAdapter constructor(
    private val studentList: List<UserDomain>
) : RecyclerView.Adapter<StudentListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: LayoutForStudentItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: UserDomain) {
            binding.studentName.text = String.format("%s, %s", student.lastName, student.firstName)
            binding.regNo.text = student.regNumber
            binding.studentPhoto.loadImage(student.imageUrl, placeholder = R.drawable.user_profile_img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutForStudentItemViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(studentList[position])
    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}
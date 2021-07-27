package com.android.asa.ui.staff_advisor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.android.asa.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {

    lateinit var binding: FragmentStudentListBinding

    lateinit var studentListAdapter: StudentListAdapter

    private val args: StudentListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentListBinding.inflate(inflater)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        studentListAdapter = StudentListAdapter(args.studentLevelInfo.students)
        binding.level.text = args.studentLevelInfo.level
        binding.studentList.apply {
            adapter = studentListAdapter
        }
    }
}
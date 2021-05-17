package com.android.asa.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.MainActivity
import com.android.asa.databinding.FragmentHomeBinding
import com.android.asa.extensions.showToast
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

import com.android.asa.ui.common.BaseFragment
import com.android.asa.utils.Result
import com.asa.domain.model.CourseDomain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: FragmentHomeBinding

    lateinit var classesAdapter: TodaysClassesAdapter

    private val todayClasses = mutableListOf<CourseDomain>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setUpRv()
        setupBarChart()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun setUpRv() {
        classesAdapter = TodaysClassesAdapter(todayClasses)
        binding.recyclerView.apply {
            adapter = classesAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }

    private fun observeData() {
        viewModel.todayCourses.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    progressDialog.apply {
                        setMessage("fetching classes for today")
                        show()
                    }
                }

                is Result.Success -> {
                    todayClasses.clear()
                    result.data?.toList()?.let { todayClasses.addAll(it)
                    Log.d("List number", it.size.toString())}
                    classesAdapter.notifyDataSetChanged()
                    progressDialog.dismiss()

                }
                is Result.Error -> {
                    progressDialog.dismiss()
                    showToast(result.errorMessage)
                }
            }
        })
    }

    private fun setupBarChart() {
        val barChart = binding.readingProgressbarChart

        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(1f, 0.5f))
        entries.add(BarEntry(2f, 1f))
        entries.add(BarEntry(3f, 2f))
        entries.add(BarEntry(4f, 3f))
        entries.add(BarEntry(5f, 4f))
        entries.add(BarEntry(6f, 5f))

        val barDataSet = BarDataSet(entries, "Cells")


        val labels = ArrayList<String>()
        //the first label is ignored.
        labels.add("CPE 511")
        labels.add("GRE 312")
        labels.add("CPE513")
        labels.add("CPE514")
        labels.add("CPE518")
        labels.add("ELE514")
        labels.add("ELE514")


        barChart.labelFor

        val data = BarData(barDataSet)
        barChart.data = data // set the data and list of labels into chart
        Description().text = ("Testing here") // set the description
        barDataSet.setColors(*ColorTemplate.LIBERTY_COLORS)
        barChart.animateY(5000)

        val xAxis: XAxis = barChart.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return labels[value.toInt()]
            }
        }
    }


}
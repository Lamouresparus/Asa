package com.android.asa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.asa.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

import com.android.asa.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        //findNavController().navigate(BeginSemesterFragmentDirections.actionBeginSemesterFragmentToAddSemesterCoursesFragment())
        setUpRv()
        setupBarChart()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpRv() {

    }

    private fun setupBarChart() {
        val barChart = binding.readingProgressbarChart as BarChart

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
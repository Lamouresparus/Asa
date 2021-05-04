package com.android.asa.ui.reading_time_set_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.asa.R
import com.android.asa.databinding.FragmentAllSetBinding
import com.android.asa.extensions.makeSpannable

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AllSetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllSetFragment : Fragment() {
    private lateinit var binding: FragmentAllSetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAllSetBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_set, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text: String = binding.textView12.text.toString()
        binding.textView12.text = text.makeSpannable(16, 20, color = resources.getColor(R.color.colorPrimary))


    }

    companion object {

        fun newInstance(param1: String, param2: String) =
                AllSetFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
package com.android.asa.ui.reading_ui

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.android.asa.databinding.FragmentReadingTimerBinding
import com.android.asa.extensions.makeGone
import com.android.asa.extensions.makeInvisible
import com.android.asa.extensions.makeVisible
import com.android.asa.ui.common.BaseFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class ReadingTimerFragment : BaseFragment() {
    private lateinit var binding: FragmentReadingTimerBinding


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReadingTimerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpOnClicks()


    }

    fun setUpTimer(){
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                val timer: Disposable = Observable
                        .interval(1, TimeUnit.SECONDS)
                        .subscribe ({ time ->
                            val minutes: Long = time / 60.toLong()
                            val second: Long = time % 60.toLong()
                            val hour = time / 3600
                            binding.timerSecs.setText(second.toString())
                            binding.timerMins.setText(minutes.toString())
                            if (hour!= 0L){
                                binding.timerHrs.setText(hour.toString())
                            }
                            Log.d("sauce", "$hour: $minutes :$second")
                        },{
                            Log.d("sauce",it.toString())
                        })
            }

        }

    }


    private fun setUpOnClicks() {

        binding.startReading.setOnClickListener {
            binding.startReading.makeGone()
            binding.snooze.setText("Pause")
            binding.dismiss.setText("Stop")
            binding.timerClockContainer.makeInvisible()
            binding.timerContainer.makeVisible()
            setUpTimer()

        }
    }


}
package com.android.asa.ui.reading_ui

import android.content.*
import android.opengl.Visibility
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.akexorcist.snaptimepicker.SnapTimePickerDialog
import com.akexorcist.snaptimepicker.TimeRange
import com.akexorcist.snaptimepicker.TimeValue
import com.android.asa.R
import com.android.asa.databinding.FragmentReadingTimerBinding
import com.android.asa.extensions.makeGone
import com.android.asa.extensions.makeInvisible
import com.android.asa.extensions.makeVisible
import com.android.asa.ui.common.BaseFragment
import com.android.asa.utils.Constants
import com.android.asa.utils.isServiceRunningInForeground
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
    private var curTimeInMillis = 0L
    private var isTimerOn = false
    private var isReadingDurationReached = false
    private val intentToService by lazy {
        Intent(requireContext(), CountUpTimerService::class.java)
    }
    private lateinit var timerService: CountUpTimerService
    private var isBound = MutableLiveData(false)
    private val receiver: TimerStatusReceiver by lazy {
        TimerStatusReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isBound.postValue(
                requireContext().isServiceRunningInForeground(CountUpTimerService::class.java)
        )

        isBound.observe(this) { isActive ->
            lifecycleScope.launch {
                updateUI(isActive)
            }
        }
    }

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


    override fun onResume() {
        super.onResume()
        isBound.postValue(requireContext().isServiceRunningInForeground(CountUpTimerService::class.java))
        LocalBroadcastManager.getInstance(requireContext())
                .registerReceiver(receiver, IntentFilter(Constants.ACTION_TIME_KEY))
    }

    private fun startTimerService() {
        requireActivity().startService(intentToService)
        requireActivity().bindService(intentToService, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopTimerService() {
        if (isBound.value!!) {
            requireActivity().unbindService(mServiceConnection)
            isBound.postValue(false)
        }
        requireActivity().stopService(intentToService)
    }


    private fun updateUI(isStart: Boolean) {
        if (isStart) {
            // when the activity going to be Destroyed, the service will be Unbind from activity,
            // But is still running in foreground. So when you start the app again, you should
            // bind the activity to service again.
            requireActivity().bindService(intentToService, mServiceConnection, Context.BIND_AUTO_CREATE)

            /*timerService.timeFlow.onEach { time ->
                binding.timer.text = time
            }.launchIn(lifecycleScope)*/

//            binding.btnStartStop.setBackgroundColor(resources.getColor(Color.red))
//            binding.btnStartStop.text = getString(R.string.btn_stop)
        } else {
//            binding.btnStartStop.setBackgroundColor(resources.getColor(Color.green))
//            binding.btnStartStop.text = getString(R.string.btn_start)
        }
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            //isBound.postValue(false)
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as CountUpTimerService.TimerBinder
            timerService = myBinder.service
            isBound.postValue(true)
        }
    }

//    fun setUpTimer() {
//        GlobalScope.launch {
//            withContext(Dispatchers.Main) {
//                val timer: Disposable = Observable
//                        .interval(1, TimeUnit.SECONDS)
//                        .subscribe({ time ->
//                            val minutes: Long = time / 60.toLong()
//                            val second: Long = time % 60.toLong()
//                            val hour = time / 3600
//                            binding.timerSecs.setText(second.toString())
//                            binding.timerMins.setText(minutes.toString())
//                            if (hour != 0L) {
//                                binding.timerHrs.setText(hour.toString())
//                            }
//                            Log.d("sauce", "$hour: $minutes :$second")
//                        }, {
//                            Log.d("sauce", it.toString())
//                        })
//            }
//
//        }
//
//    }



    private fun setUpOnClicks() {

        binding.startReading.setOnClickListener {
            binding.startReading.makeGone()
            binding.snooze.text = "Pause"
            binding.dismiss.text = "Stop"
            binding.timerClockContainer.makeInvisible()
            binding.timerContainer.makeVisible()

            startTimerService()
        }

        binding.snoozeContainer.setOnClickListener {
            if (isTimerOn) {

            } else if (isReadingDurationReached) {
                showExtendTimeDialog()

            } else {

            }

        }

        binding.dismissContainer.setOnClickListener {

        }


    }

    fun showExtendTimeDialog() {
        SnapTimePickerDialog.Builder().apply {
            setTitle(R.string.title)
            setPrefix(R.string.time_suffix)
            setSuffix(R.string.time_prefix)
            setThemeColor(R.color.colorAccent)
            setTitleColor(R.color.colorTextPrimary)
            setPositiveButtonText(R.string.extend_by)
            setPositiveButtonColor(R.color.white)
            setButtonTextAllCaps(true)
            setPreselectedTime(TimeValue(1, 0))
            setSelectableTimeRange(TimeRange(TimeValue(0, 30), TimeValue(11, 0)))

        }.build().apply {
            setListener { hour, minute ->
                // Do something when user selected the time
            }
        }.show(childFragmentManager, tag)

    }

    /**
     * used to get events from foreground service
     */
    inner class TimerStatusReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Constants.ACTION_TIME_KEY -> {
                    if (intent.hasExtra(Constants.ACTION_TIME_VALUE)) {
                        val intentExtra = intent.getStringExtra(Constants.ACTION_TIME_VALUE)

                        if (intentExtra == Constants.ACTION_TIMER_STOP) {
                            stopTimerService()
                        } else {
                            binding.timer.text = intent.getStringExtra(Constants.ACTION_TIME_VALUE)
                        }
                    }
                }
            }
        }
    }

}
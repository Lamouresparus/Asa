package com.android.asa.ui.countup_reading_timer_ui

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.akexorcist.snaptimepicker.SnapTimePickerDialog
import com.akexorcist.snaptimepicker.TimeRange
import com.akexorcist.snaptimepicker.TimeValue
import com.android.asa.R
import com.android.asa.databinding.FragmentReadingTimerBinding
import com.android.asa.extensions.makeGone
import com.android.asa.extensions.makeInvisible
import com.android.asa.extensions.makeVisible
import com.android.asa.ui.common.BaseFragment
import com.android.asa.ui.profile.ProfileViewModel
import com.android.asa.utils.Constants
import com.android.asa.utils.isServiceRunningInForeground
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ReadingTimerFragment : BaseFragment() {

    private lateinit var binding: FragmentReadingTimerBinding
    private var curTimeInMillis = 0L
    private var isTimerOn = false
    private var isReadingDurationReached = false
    var totalReadingTimeInMillis = 0L

    private val viewModel by activityViewModels<ProfileViewModel>()

    // For testing purpose only , this is equal to 1 minute
    val timeReached = 60000L

    private val args: ReadingTimerFragmentArgs by navArgs()

    private val intentToService by lazy {
        Intent(requireActivity(), CountUpTimerService::class.java)
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
        setUpOnClickListeners()

        viewModel.saveUserCourse(args.userCourses)
        binding.courseCode.text = args.userCourses.courseCode


        //Its set to true if the user wants to open this fragment from the Intent received from the Notification
        //Its set to true in the mainActivity
        if (viewModel.showTimerCountDown) {
            hideClockView()

        }
    }

    override fun onResume() {
        super.onResume()
        isBound.postValue(requireContext().isServiceRunningInForeground(CountUpTimerService::class.java))
        LocalBroadcastManager.getInstance(requireContext())
                .registerReceiver(receiver, IntentFilter(Constants.ACTION_TIME_KEY))
    }

    private fun startTimerService() {
        isTimerOn = true
        requireActivity().startService(intentToService)
        requireActivity().bindService(intentToService, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopTimerService() {
        if (isBound.value!!) {
            isTimerOn = false
            isReadingDurationReached = false
            activity?.unbindService(mServiceConnection)
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
        } else {

        }
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            //isBound.postValue(false)
        }


        /**
            timeReached is the number of hours specified by the user he wants to read
           time is the number of milliseconds since the user has started reading
           if the current time is greater than timeReached , then the user has achieved the number of hours/minutes he wants to read
           he can decide to extend it or not

           totalReadingTimeInMillis is the total time the user has read

            */

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as CountUpTimerService.TimerBinder

            timerService = myBinder.service
            timerService.userCourseData = args.userCourses
            isBound.postValue(true)
            timerService.totalTimeInMilli.observe(viewLifecycleOwner, Observer {time->


                totalReadingTimeInMillis = time


                if (time > timeReached) {
                    stopTimerService()
                    isReadingDurationReached = true
                    binding.readingDurationReachedContainer.makeVisible()
                    binding.snooze.text = "Extend"
                    binding.dismiss.text = "Finish"

                }
            })
        }
    }

/**
This function is used to hide the CLOCK View when the user has started reading
 */
    fun hideClockView() {
        binding.startReading.makeGone()
        binding.snooze.text = "Pause"
        binding.dismiss.text = "Stop"
        binding.timerClockContainer.makeInvisible()
        binding.timerContainer.makeVisible()
    }

    private fun setUpOnClickListeners() {
        binding.startReading.setOnClickListener {
            hideClockView()
            startTimerService()
        }

        binding.snoozeContainer.setOnClickListener {
            when {
                isTimerOn -> {
                    stopTimerService()
                }
                isReadingDurationReached -> {
                    showExtendTimeDialog()
                }
                else -> {
                    // Snooze
                }
            }

        }

        binding.dismissContainer.setOnClickListener {

            if (isReadingDurationReached) {
                binding.readingDurationReachedContainer.makeInvisible()
                findNavController().navigate(R.id.action_readingTimerFragment_to_readingCompleteFragment)
            }
            stopTimerService()
            binding.startReading.makeVisible()
            binding.timerClockContainer.makeVisible()
            binding.timerContainer.makeInvisible()
            binding.snooze.text = "Snooze"
            binding.dismiss.text = "Dismiss"
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

    }


    /**
    This is function is used to show the extend time Dialog
     */

    private fun showExtendTimeDialog() {
        SnapTimePickerDialog.Builder().apply {
            setTitle(R.string.title)
            setPrefix(R.string.time_prefix)
            setSuffix(R.string.time_suffix)
            setThemeColor(R.color.colorPrimary)
            setTitleColor(R.color.colorTextPrimary)
            setPositiveButtonText(R.string.extend_by)
            setPositiveButtonColor(R.color.colorPrimary)
            setButtonTextAllCaps(true)
            setPreselectedTime(TimeValue(1, 0))
            setSelectableTimeRange(TimeRange(TimeValue(0, 30), TimeValue(11, 0)))

        }.build().apply {
            setListener { hour, minute ->

            }
        }.show(childFragmentManager, tag)

    }

    /**
     * used to get events from foreground service and display the time
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
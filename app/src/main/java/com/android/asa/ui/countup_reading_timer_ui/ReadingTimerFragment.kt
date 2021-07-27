package com.android.asa.ui.countup_reading_timer_ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
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
import com.android.asa.extensions.milliSecondsToTime
import com.android.asa.ui.common.BaseFragment
import com.android.asa.ui.countup_reading_timer_ui.ReadingTimerService.Companion.ACTION_READING_TIMER
import com.android.asa.ui.countup_reading_timer_ui.ReadingTimerService.Companion.READING_TIMER_TEXT
import com.android.asa.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

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

    private val receiver: TimerReceiver by lazy {
        TimerReceiver()
    }

    private val broadCastManager by lazy {
        LocalBroadcastManager.getInstance(requireContext())
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

        viewModel.saveUserCourse(args.userCourse)
        binding.courseCode.text = args.userCourse.course

        //Its set to true if the user wants to open this fragment from the Intent received from the Notification
        //Its set to true in the mainActivity
        if (viewModel.showTimerCountDown) {
            hideClockView()
        }
    }

    override fun onResume() {
        super.onResume()
        broadCastManager.registerReceiver(receiver, IntentFilter(ACTION_READING_TIMER))
    }

    override fun onPause() {
        super.onPause()
        broadCastManager.unregisterReceiver(receiver)
    }

    private fun startTimerService() = sendCommandToForegroundService(TimerActions.START)

    private fun stopTimerService() = sendCommandToForegroundService(TimerActions.STOP)

    private fun sendCommandToForegroundService(action: TimerActions) {
        ContextCompat.startForegroundService(requireContext(), getServiceIntent(action))
    }

    private fun getServiceIntent(command: TimerActions) = ReadingTimerService.intent(requireContext(), command)

    /**
    This function is used to hide the CLOCK View when the user has started reading
     */
    private fun hideClockView() {
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
            handleTimerOperations()
        }
//            when {
//                isTimerOn -> {
//                    stopTimerService()
//                    isTimerOn = false
//                }
//
//                !isTimerOn -> {
//                    startTimerService()
//                    isTimerOn = true
//                }
//
//                isReadingDurationReached -> {
//                    showExtendTimeDialog()
//                }
//                else -> {
//                    // Snooze
//                }
//            }
//
//        }

        binding.dismissContainer.setOnClickListener { dismissTimer() }

        binding.backBtn.setOnClickListener {
            if (ReadingTimerService.getTimerState() == TimerState.RUNNING) {
                dismissTimer()
            } else {
                findNavController().navigateUp()
            }
        }
    }

    private fun dismissTimer() {
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

    private fun handleTimerOperations() {
        when (ReadingTimerService.getTimerState()) {
            TimerState.PAUSED -> {
                binding.snooze.text = getString(R.string.pause)
                sendCommandToForegroundService(TimerActions.RESUME)
            }
            TimerState.RUNNING -> {
                binding.snooze.text = getString(R.string.resume)
                sendCommandToForegroundService(TimerActions.PAUSE)
            }
            TimerState.STOPPED, TimerState.IDLE -> {// TODO nothing here}
            }
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

    private fun updateUi(elapsedTime: Long) {
        binding.timer.text = elapsedTime.milliSecondsToTime()
    }

    /**
     * receive time update form the service
     */
    inner class TimerReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ACTION_READING_TIMER) {
                updateUi(intent.getLongExtra(READING_TIMER_TEXT, 0))
            }
        }
    }
}
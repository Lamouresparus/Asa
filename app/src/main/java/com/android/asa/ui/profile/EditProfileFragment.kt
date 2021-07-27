package com.android.asa.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.asa.R
import com.android.asa.databinding.FragmentEditProfileBinding
import com.android.asa.databinding.LayoutItemForUserCoursesBinding
import com.android.asa.extensions.loadImage
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.android.asa.ui.widget.RecyclerViewAdapter
import com.android.asa.ui.widget.ViewHolder
import com.android.asa.ui.countup_reading_timer_ui.UserCourses
import com.android.asa.utils.Result
import com.asa.domain.model.UserDomain
import com.classic.chatapp.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentEditProfileBinding

    private val viewModel by viewModels<EditProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
        setUpRecycler()
        observeData()
    }

    private fun observeData() {
        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner, Observer {
            bindUserDataToUI(it)
        })

        viewModel.updateProfile.observe(viewLifecycleOwner, EventObserver { result ->
            when (result) {
                is Result.Loading -> {
                    progressDialog.apply {
                        setMessage("updating data")
                        show()
                    }
                }
                is Result.Success -> {
                    progressDialog.dismiss()
                    showToast("profile updated")
                }
                is Result.Error -> {
                    progressDialog.dismiss()
                }
            }

        })
    }

    private fun getUserParams(): UserDomain {
        val institution = binding.userInstitution.text.trim().toString()
        val department = binding.userDepartment.text.trim().toString()
        val userBio = binding.userBio.text.trim().toString()
        return viewModel.getCurrentUser().copy(
            institution = institution,
            department = department,
            userBio = userBio
        )
    }

    private fun bindUserDataToUI(user: UserDomain) {
        binding.changedProfileImage.loadImage(user.imageUrl, placeholder = R.drawable.user_profile_img)
        binding.userBio.setText(user.userBio)
        if (user.firstName != null && user.lastName != null) {
            binding.userName.text = String.format("%s, %s", user.lastName, user.firstName)
        }

        if (user.department != null && user.institution != null) {
            binding.userDepartment.setText(user.department)
            binding.userInstitution.setText(user.institution)
        }
    }

    private val imagePickerIntent by lazy {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
    }

    fun setUpClickListeners() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.changedProfileImage.setOnClickListener {
            startActivityForResult(imagePickerIntent, REQUEST_CODE_FOR_PROFILE_IMAGE)
        }

        binding.saveChangesBtn.setOnClickListener {
            viewModel.updateUserData(getUserParams())
        }
    }

    private fun setUpRecycler() {
        binding.coursesRecyclerView.apply {
            adapter = coursesAdapter
            layoutManager = GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setProfileImage(uri: Uri?) {
        binding.userImage.setImageURI(uri)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_FOR_PROFILE_IMAGE -> {
                    setProfileImage(data?.data)
                }

            }
        }
    }

    private val coursesAdapter =
        object : RecyclerViewAdapter<UserCourses>(
            CoursesDiffUtil()
        ) {
            override fun getLayoutRes(model: UserCourses): Int {
                return R.layout.layout_item_for_user_courses
            }

            override fun getViewHolder(
                view: View,
                recyclerViewAdapter: RecyclerViewAdapter<UserCourses>
            ): ViewHolder<UserCourses> {
                return ProfileViewHolder(LayoutItemForUserCoursesBinding.bind(view), onCourseItemClickCallBack)
            }
        }

    private val onCourseItemClickCallBack: (UserCourses) -> Unit = { course ->

        val bundle = Bundle().apply {
            putParcelable("userCourses", course)
        }
        findNavController().navigate(
            R.id.action_editProfileFragment_to_readingTimerFragment,
            bundle
        )
    }

    companion object {
        const val REQUEST_CODE_FOR_PROFILE_IMAGE = 300
    }
}
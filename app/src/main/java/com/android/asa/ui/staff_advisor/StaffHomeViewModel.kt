package com.android.asa.ui.staff_advisor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.asa.extensions.asLiveData
import com.android.asa.ui.common.BaseViewModel
import com.android.asa.ui.staff_advisor.models.StudentLevelInfo
import com.asa.data.sharedPreference.SharedPreferenceReader
import com.asa.domain.model.UserDomain
import com.asa.remotes.RemoteDataSourceImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import durdinapps.rxfirebase2.RxFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffHomeViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val preferenceReader: SharedPreferenceReader
) : BaseViewModel() {

    private val viewStateEmitter = MutableLiveData<StaffHomeViewState>()
    val viewState = viewStateEmitter

    private val _user = MutableLiveData<UserDomain>()
    val user = _user.asLiveData()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = preferenceReader.getUserData()
            _user.postValue(user ?: UserDomain())
        }
    }

    fun getStudentLevelsInfo() {
        val collectionRef = firestore.collection(RemoteDataSourceImpl.USERS_COLLECTION_PATH)
        RxFirestore.getCollection(collectionRef).toSingle().map {
            val documents = it.documentChanges
            documents.map { doc ->
                doc.document.toObject(UserDomain::class.java)
            }.filter { user ->
                user.userType == 0
            }
        }.map { mapStudentListToStudentLevelsInfo(it) }
            .doOnSubscribe {
                viewStateEmitter.postValue(StaffHomeViewState.Loading)
            }
            .subscribe(
                {
                    viewStateEmitter.postValue(StaffHomeViewState.Content(it))
                },
                {
                }
            ).addToContainer()
    }

    sealed class StaffHomeViewState {
        object Loading : StaffHomeViewState()
        data class Content(val studentLevelsInfo: List<StudentLevelInfo>) : StaffHomeViewState()
    }

    private fun mapStudentListToStudentLevelsInfo(list: List<UserDomain>): MutableList<StudentLevelInfo> {
        studentLevelInfoList.forEach { info ->
            info.numberOfStudents = list.count { it.level == info.id }
        }
        return studentLevelInfoList
    }

    private val studentLevelInfoList = mutableListOf(
        StudentLevelInfo(100, "100 Level", "Freshmen"),
        StudentLevelInfo(200, "200 Level", "Sophomore"),
        StudentLevelInfo(300, "300 Level", "Junior"),
        StudentLevelInfo(400, "400 Level", "Senior"),
        StudentLevelInfo(500, "500 Level", "Final Year"),
        StudentLevelInfo(600, "600 Level", "Extra Year 1"),
        StudentLevelInfo(700, "700 Level", "Extra Year 2"),
    )
}
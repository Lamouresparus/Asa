package com.android.asa.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.asa.extensions.asLiveData
import com.android.asa.ui.common.BaseViewModel
import com.android.asa.utils.Event
import com.android.asa.utils.Result
import com.asa.data.sharedPreference.SharedPreferenceReader
import com.asa.domain.UpdateUserDataUseCase
import com.asa.domain.model.UserDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val preferenceReader: SharedPreferenceReader,
    private val updateUserDataUseCase: UpdateUserDataUseCase
) : BaseViewModel() {

    private val _user = MutableLiveData<UserDomain>()
    val user = _user.asLiveData()

    private val _updateProfile = MutableLiveData<Event<Result<Unit>>>()
    val updateProfile = _updateProfile.asLiveData()

    fun getCurrentUser() = preferenceReader.getUserData() ?: throw UninitializedPropertyAccessException()

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = preferenceReader.getUserData()
            _user.postValue(user ?: UserDomain())
        }
    }

    fun updateUserData(userDomain: UserDomain) {
        updateUserDataUseCase
            .execute(userDomain)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _updateProfile.postValue(Event(Result.Loading))
            }
            .subscribe(
                {
                    _updateProfile.postValue(Event(Result.Success()))
                },
                {
                    _updateProfile.postValue(Event(Result.Error(it.message.toString())))
                }
            )
            .addToContainer()
    }
}
package com.android.asa.ui.auth

import androidx.lifecycle.MutableLiveData
import com.android.asa.extensions.asLiveData
import com.android.asa.ui.common.BaseViewModel
import com.android.asa.utils.Result
import com.asa.domain.LogInUseCase
import com.asa.domain.RegisterUseCase
import com.classic.chatapp.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
        private val registerUser: RegisterUseCase,
        private val logInUseCase: LogInUseCase,
) :
        BaseViewModel() {

    private var _registerResponse = MutableLiveData<Event<Result<Unit>>>()
    val registerResponse = _registerResponse.asLiveData()


    private var _loginResponse = MutableLiveData<Event<Result<Unit>>>()
    val loginResponse = _loginResponse.asLiveData()

    fun registerUser(input: RegisterUseCase.Params) {
        registerUser.execute(input)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _registerResponse.postValue(Event(Result.Loading)) }
                .subscribe({ _registerResponse.postValue(Event(Result.Success())) },
                        { _registerResponse.postValue(Event(Result.Error(it.message.toString()))) }
                ).addToContainer()
    }

    fun loginUser(params: LogInUseCase.Params) {

        logInUseCase.execute(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _loginResponse.postValue(Event(Result.Loading)) }
                .subscribe(
                        { _loginResponse.postValue(Event(Result.Success())) },
                        { _loginResponse.postValue(Event(Result.Error(it.message.toString()))) }
                ).addToContainer()

    }
}
package com.asa.domain

import com.asa.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class LogInUseCase @Inject constructor(private val userRepository: UserRepository) :
        BaseUseCase<LogInUseCase.Params, Completable> {

    override fun execute(param: Params?): Completable {
        param ?: throw UnsupportedOperationException("login params not provided")
        return userRepository.login(param)
    }

    data class Params(
            val email: String,
            val password: String,
            val userType: Int
    )
}
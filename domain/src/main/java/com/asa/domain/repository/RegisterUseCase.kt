package com.asa.domain.repository

import com.asa.domain.BaseUseCase
import io.reactivex.Completable
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val userRepository: UserRepository) :
        BaseUseCase<RegisterUseCase.Params, Completable> {

    override fun execute(param: Params?): Completable {
        param ?: throw UnsupportedOperationException("login params not provided")
        return userRepository.register(param)
    }

    data class StudentParams(
            override val email: String,
            override val password: String,
            val studentRegistrationNumber: String
    ) : Params

    data class StaffParams(
            override val email: String,
            override val password: String,
            val staffIdentificationNumber: String
    ) : Params

    interface Params {
        val email: String
        val password: String
    }
}
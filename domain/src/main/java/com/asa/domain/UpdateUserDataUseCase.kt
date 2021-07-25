package com.asa.domain

import com.asa.domain.model.UserDomain
import com.asa.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class UpdateUserDataUseCase @Inject constructor(private val userRepository: UserRepository) :
    BaseUseCase<UserDomain, Completable> {
    override fun execute(param: UserDomain?): Completable {
        param ?: throw UnsupportedOperationException("uer params not provided")
        return userRepository.updateUserData(param)
    }
}
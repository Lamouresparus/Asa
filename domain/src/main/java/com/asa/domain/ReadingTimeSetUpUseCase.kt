package com.asa.domain

import com.asa.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class ReadingTimeSetUpUseCase @Inject constructor(private val userRepository: UserRepository) :
        BaseUseCase<ReadingTimeSetUpUseCase.Params, Completable> {

    override fun execute(param: Params?): Completable {
        param ?: throw UnsupportedOperationException("reading params not provided")
        return userRepository.saveReadingTime(param)
    }


    data class Params(
            var preferredReadingPeriod: String,
            var kindOfReader: String,
            var averageReadingTime: String,
    )
}
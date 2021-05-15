package com.asa.domain

import com.asa.domain.model.CourseDomain
import com.asa.domain.repository.SemesterRepository
import io.reactivex.Completable
import javax.inject.Inject

class StartNewSemesterUseCase @Inject constructor(private val semesterRepository: SemesterRepository) :
    BaseUseCase<StartNewSemesterUseCase.Params, Completable> {

    override fun execute(param: Params?): Completable {
        return semesterRepository.startNewSemester()
    }

    data class Params(
        val course: CourseDomain
    )
}
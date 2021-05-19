package com.asa.domain.repository

import io.reactivex.Completable

interface SemesterRepository {
    fun startNewSemester(): Completable
}
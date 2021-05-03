package com.asa.domain

interface BaseUseCase<Param, Result> {
    fun execute(param: Param? = null): Result
}
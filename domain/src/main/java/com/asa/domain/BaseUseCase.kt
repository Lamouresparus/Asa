package com.asa.domain

interface BaseUseCase<Param, Result> {
    fun execute(input: Param? = null): Result
}
package com.asa.domain.model

data class ReadingTimeSetupDomain(
        val readingPeriod: String? = null,
        val kindOfReader: String? = null,
        val averageReadingPeriod: String? = null
)
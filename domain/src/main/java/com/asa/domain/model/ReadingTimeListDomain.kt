package com.asa.domain.model

data class ReadingTimeListDomain(
    var readingTimeList: List<ReadingTimeDomain>
){
    constructor() : this(emptyList())
}
package com.asa.domain.model

data class ReadingTimePreferencesDomain(
    var preferredReadDay: Int,
    var preferredReadTime: Int,
    var averageReadingHours: Int,
){
    constructor() : this(-1, -1, -1)
}

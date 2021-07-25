package com.android.asa.utils

object ColorUtils {
    //ASA course card colors
    private val colors = listOf("#00BBBA", "#72ED77", "#FFAD00", "#EB5757", "#BB6BD9", "#4F4F4F")

    fun getCourseCardColor(itemPosition: Int): String {
        var colorPosition = itemPosition
        if (colorPosition > colors.size - 1) {
            colorPosition = (itemPosition - 1) % (colors.size - 1)
        }
        return colors[colorPosition]
    }
}
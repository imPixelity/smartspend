package com.pemmob.smartspend.ui.components

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun formatDisplayDate(dateString: String): String {
    if (dateString.isBlank()) return dateString
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = inputFormat.parse(dateString) ?: return dateString

        val targetCal = Calendar.getInstance().apply { time = date }
        val todayCal = Calendar.getInstance()
        val yesterdayCal = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }

        val isSameDay = { cal1: Calendar, cal2: Calendar ->
            cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
        }

        when {
            isSameDay(targetCal, todayCal) -> "Hari ini"
            isSameDay(targetCal, yesterdayCal) -> "Kemarin"
            else -> {
                val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
                outputFormat.format(date)
            }
        }
    } catch (e: Exception) {
        dateString
    }
}

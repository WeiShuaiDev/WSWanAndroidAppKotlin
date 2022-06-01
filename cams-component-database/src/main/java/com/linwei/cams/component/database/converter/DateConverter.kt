package com.linwei.cams.component.database.converter

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    fun revertDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun converterDate(value: Date?): Long {
        var date = value
        if (date == null) {
            date = Date()
        }
        return date.time
    }
}
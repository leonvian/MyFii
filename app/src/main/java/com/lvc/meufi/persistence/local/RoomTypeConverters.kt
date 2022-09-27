package com.lvc.meufi.persistence.local

import androidx.room.TypeConverter
import java.util.Date

class RoomTypeConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}
package com.manhwamc.app.util

import androidx.room.TypeConverter
import com.manhwamc.app.data.model.MissionStatus
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromMissionStatus(status: MissionStatus): String {
        return status.name
    }

    @TypeConverter
    fun toMissionStatus(status: String): MissionStatus {
        return MissionStatus.valueOf(status)
    }
}
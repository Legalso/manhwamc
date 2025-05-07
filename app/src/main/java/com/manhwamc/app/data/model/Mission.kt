package com.manhwamc.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "missions")
data class Mission(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val xp: Int,
    val status: MissionStatus,
    val createdAt: Date,
    val completedAt: Date? = null
)

enum class MissionStatus {
    ACTIVE,
    COMPLETED
}
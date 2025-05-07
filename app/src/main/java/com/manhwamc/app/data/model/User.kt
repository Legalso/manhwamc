package com.manhwamc.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String = "current_user",
    val totalXp: Int = 0,
    val currentLevel: Int = 1
) {
    companion object {
        const val XP_PER_LEVEL = 1000
    }

    fun getXpForNextLevel(): Int {
        return currentLevel * XP_PER_LEVEL
    }

    fun getXpProgress(): Float {
        return (totalXp % XP_PER_LEVEL).toFloat() / XP_PER_LEVEL
    }
}
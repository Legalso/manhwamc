package com.manhwamc.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.manhwamc.app.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUser(userId: String = "current_user"): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE users SET totalXp = totalXp + :xp WHERE id = :userId")
    suspend fun addXp(xp: Int, userId: String = "current_user")

    @Query("UPDATE users SET currentLevel = currentLevel + 1 WHERE id = :userId")
    suspend fun levelUp(userId: String = "current_user")
}
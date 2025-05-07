package com.manhwamc.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.manhwamc.app.data.model.Mission
import com.manhwamc.app.data.model.MissionStatus

@Dao
interface MissionDao {
    @Query("SELECT * FROM missions WHERE status = :status ORDER BY createdAt DESC")
    fun getMissionsByStatus(status: MissionStatus): LiveData<List<Mission>>

    @Insert
    suspend fun insertMission(mission: Mission)

    @Update
    suspend fun updateMission(mission: Mission)

    @Delete
    suspend fun deleteMission(mission: Mission)

    @Query("SELECT * FROM missions WHERE id = :id")
    suspend fun getMissionById(id: Long): Mission?

    @Query("SELECT * FROM missions WHERE status = :status LIMIT 1")
    suspend fun getCurrentMission(status: MissionStatus = MissionStatus.ACTIVE): Mission?
}
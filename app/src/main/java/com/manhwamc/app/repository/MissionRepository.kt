package com.manhwamc.app.repository

import androidx.lifecycle.LiveData
import com.manhwamc.app.data.dao.MissionDao
import com.manhwamc.app.data.dao.UserDao
import com.manhwamc.app.data.model.Mission
import com.manhwamc.app.data.model.MissionStatus
import com.manhwamc.app.data.model.User
import java.util.Date

class MissionRepository(
    private val missionDao: MissionDao,
    private val userDao: UserDao
) {
    val activeMissions: LiveData<List<Mission>> = missionDao.getMissionsByStatus(MissionStatus.ACTIVE)
    val completedMissions: LiveData<List<Mission>> = missionDao.getMissionsByStatus(MissionStatus.COMPLETED)
    val currentUser: LiveData<User> = userDao.getUser()

    suspend fun addMission(title: String, xp: Int) {
        val mission = Mission(
            title = title,
            xp = xp,
            status = MissionStatus.ACTIVE,
            createdAt = Date()
        )
        missionDao.insertMission(mission)
    }

    suspend fun completeMission(missionId: Long) {
        val mission = missionDao.getMissionById(missionId) ?: return
        val updatedMission = mission.copy(
            status = MissionStatus.COMPLETED,
            completedAt = Date()
        )
        missionDao.updateMission(updatedMission)
        userDao.addXp(mission.xp)

        // Verificar se o usuário subiu de nível
        val user = userDao.getUser().value ?: return
        if (user.totalXp >= user.getXpForNextLevel()) {
            userDao.levelUp()
        }
    }

    suspend fun getCurrentMission(): Mission? {
        return missionDao.getCurrentMission()
    }
}
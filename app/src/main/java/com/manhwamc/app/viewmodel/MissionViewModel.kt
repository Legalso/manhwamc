package com.manhwamc.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.manhwamc.app.data.AppDatabase
import com.manhwamc.app.data.model.Mission
import com.manhwamc.app.data.model.User
import com.manhwamc.app.repository.MissionRepository
import kotlinx.coroutines.launch

class MissionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MissionRepository
    val activeMissions: LiveData<List<Mission>>
    val completedMissions: LiveData<List<Mission>>
    val currentUser: LiveData<User>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MissionRepository(database.missionDao(), database.userDao())
        activeMissions = repository.activeMissions
        completedMissions = repository.completedMissions
        currentUser = repository.currentUser
    }

    fun addMission(title: String, xp: Int) {
        viewModelScope.launch {
            repository.addMission(title, xp)
        }
    }

    fun completeMission(missionId: Long) {
        viewModelScope.launch {
            repository.completeMission(missionId)
        }
    }

    fun getCurrentMission() {
        viewModelScope.launch {
            repository.getCurrentMission()
        }
    }
}
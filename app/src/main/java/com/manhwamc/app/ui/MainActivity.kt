package com.manhwamc.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.manhwamc.app.R
import com.manhwamc.app.databinding.ActivityMainBinding
import com.manhwamc.app.ui.adapter.MissionAdapter
import com.manhwamc.app.viewmodel.MissionViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MissionViewModel by viewModels()
    private val missionAdapter = MissionAdapter { mission ->
        viewModel.completeMission(mission.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFab()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.missionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = missionAdapter
        }
    }

    private fun setupFab() {
        binding.addMissionFab.setOnClickListener {
            startActivity(Intent(this, AddMissionActivity::class.java))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.activeMissions.collect { missions ->
                    missionAdapter.submitList(missions)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentUser.collect { user ->
                    user?.let {
                        binding.levelText.text = getString(R.string.level_up, it.currentLevel)
                        binding.xpText.text = "XP: ${it.totalXp}/${it.getXpForNextLevel()}"
                        binding.xpProgress.progress = (it.getXpProgress() * 100).toInt()
                    }
                }
            }
        }
    }
}
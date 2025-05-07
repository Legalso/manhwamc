package com.manhwamc.app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.manhwamc.app.R
import com.manhwamc.app.databinding.ActivityAddMissionBinding
import com.manhwamc.app.viewmodel.MissionViewModel

class AddMissionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMissionBinding
    private val viewModel: MissionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupSaveButton()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            val title = binding.titleInput.text.toString()
            val xpText = binding.xpInput.text.toString()

            if (title.isBlank()) {
                binding.titleInputLayout.error = getString(R.string.error_empty_title)
                return@setOnClickListener
            }

            val xp = try {
                xpText.toInt()
            } catch (e: NumberFormatException) {
                binding.xpInputLayout.error = getString(R.string.error_invalid_xp)
                return@setOnClickListener
            }

            viewModel.addMission(title, xp)
            Toast.makeText(this, R.string.mission_added, Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
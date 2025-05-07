package com.manhwamc.app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.manhwamc.app.R
import com.manhwamc.app.data.AppDatabase
import com.manhwamc.app.data.model.MissionStatus
import com.manhwamc.app.repository.MissionRepository
import kotlinx.coroutines.launch

class MissionWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val database = AppDatabase.getDatabase(context)
        val repository = MissionRepository(database.missionDao(), database.userDao())

        (ProcessLifecycleOwner.get() as LifecycleOwner).lifecycleScope.launch {
            val currentMission = repository.getCurrentMission()

            appWidgetIds.forEach { appWidgetId ->
                val views = RemoteViews(context.packageName, R.layout.mission_widget)

                if (currentMission != null) {
                    views.setTextViewText(R.id.missionTitle, currentMission.title)
                    views.setTextViewText(R.id.missionXp, "${currentMission.xp} XP")
                    views.setViewVisibility(R.id.completeButton, android.view.View.VISIBLE)
                } else {
                    views.setTextViewText(R.id.missionTitle, context.getString(R.string.widget_no_mission))
                    views.setTextViewText(R.id.missionXp, "")
                    views.setViewVisibility(R.id.completeButton, android.view.View.GONE)
                }

                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }
}
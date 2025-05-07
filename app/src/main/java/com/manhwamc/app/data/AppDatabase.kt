package com.manhwamc.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manhwamc.app.data.dao.MissionDao
import com.manhwamc.app.data.dao.UserDao
import com.manhwamc.app.data.model.Mission
import com.manhwamc.app.data.model.User
import com.manhwamc.app.util.Converters

@Database(entities = [Mission::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun missionDao(): MissionDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "manhwamc_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
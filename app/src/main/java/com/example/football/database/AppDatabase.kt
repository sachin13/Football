package com.example.football.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.football.database.entities.LeaguesEntity
import com.example.football.models.responce.standins.ResStandingsData

@Database(version = 1, entities = [LeaguesEntity::class, ResStandingsData::class])
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {


    abstract fun standingsDao(): StandingsDao
    abstract fun leaguesDao(): LeaguesDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }

            return instance!!
        }
    }


}
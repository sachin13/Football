package com.example.football.database

import androidx.room.Dao
import androidx.room.Query
import com.example.football.database.entities.LeaguesEntity

@Dao
interface LeaguesDao : BaseDao<LeaguesEntity> {
    @Query("select * from leaguesentity ")
    fun getAllLeaguesData(): List<LeaguesEntity>

    @Query("Select * from leaguesentity where id = :id")
    fun getLeagueById(id: String): LeaguesEntity
}
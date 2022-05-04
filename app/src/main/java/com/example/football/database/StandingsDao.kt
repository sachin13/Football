package com.example.football.database

import androidx.room.Dao
import androidx.room.Query
import com.example.football.models.responce.standins.ResStandingsData

@Dao
interface StandingsDao : BaseDao<ResStandingsData> {

    @Query("select * from resstandingsdata where id=:id")
    fun getStandingsByLeagueId(id: String): ResStandingsData?

}
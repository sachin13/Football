package com.example.football.models.repositories

import com.example.football.database.AppDatabase
import com.example.football.database.entities.LeaguesEntity
import com.example.football.models.responce.standins.ResStandingsData
import com.example.football.retrofit.ApiService
import kotlinx.coroutines.flow.flow

class FootballDataRepository(val apiService: ApiService, val appDatabase: AppDatabase) {


    suspend fun getAllLeagues() = flow { emit(apiService.getAllLeaguesData()) }

    suspend fun getAllStandings(id: String) = flow { emit(apiService.getStandings(id)) }

    suspend fun insertNewStandingsDb(data: ResStandingsData) =
        appDatabase.standingsDao().insert(data)

    suspend fun insertLeaguesDataToDb(data: List<LeaguesEntity>) =
        appDatabase.leaguesDao().insert(data)


}
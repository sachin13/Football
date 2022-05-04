package com.example.football.utils

import com.example.football.database.entities.LeaguesEntity

sealed class LeaguesResource {

    object Loading : LeaguesResource()


    class Error(val message: String) : LeaguesResource()

    class Success(val data: List<LeaguesEntity>) : LeaguesResource()
}

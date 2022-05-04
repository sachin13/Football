package com.example.football.utils

import com.example.football.models.responce.standins.ResStandingsData

sealed class StandingsResource {

    object Loading : StandingsResource()

    class Error(val message: String) : StandingsResource()

    class Succes(val data: ResStandingsData) : StandingsResource()
}
package com.example.football.database

import androidx.room.TypeConverter
import com.example.football.models.responce.standins.Data
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun standingToJson(data: Data): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromJsonToStanding(string: String): Data {
        val type = object : TypeToken<Data>() {}.type

        return Gson().fromJson(string, type)
    }


}
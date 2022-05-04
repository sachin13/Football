package com.example.football.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.football.database.AppDatabase
import com.example.football.utils.NetworkHelper
import java.lang.Exception

class ViewModelFactory(val appDatabase: AppDatabase, val networkHelper: NetworkHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.football.viewmodels.ViewModel::class.java)) {
            return com.example.football.viewmodels.ViewModel(appDatabase,networkHelper) as T
        }

        throw Exception("Error")
    }
}
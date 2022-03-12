package com.rahulgupta.imdbmovieapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.rahulgupta.imdbmovieapp.network.ApiService
import com.rahulgupta.imdbmovieapp.repository.db.DatabaseService
import com.rahulgupta.imdbmovieapp.utils.Helper
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltAndroidApp
class BaseApplication :Application() {

    //fun getApiService() = ApiService.getInstance()
    override fun onCreate() {
        super.onCreate()

        //generateLocalDB()
    }

//    fun generateLocalDB() : DatabaseService {
//        return Room.databaseBuilder(applicationContext, DatabaseService::class.java, "imdb-db")
//            .build()
//    }
//
//    fun getHelper(): Helper = Helper(applicationContext)
}
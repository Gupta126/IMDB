package com.rahulgupta.imdbmovieapp.di

import com.rahulgupta.imdbmovieapp.network.ApiService
import android.content.Context
import androidx.room.Room
import com.rahulgupta.imdbmovieapp.repository.db.DatabaseService
import com.rahulgupta.imdbmovieapp.utils.Helper

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getApiService() = ApiService.getInstance()

    @Provides
    fun localDb(@ApplicationContext context: Context) : DatabaseService {
        return Room.databaseBuilder(context, DatabaseService::class.java, "imdb-db")
            .build()
    }

    @Provides
    fun getHelper( @ApplicationContext context: Context):Helper=Helper(context)



}
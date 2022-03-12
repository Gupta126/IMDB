package com.rahulgupta.imdbmovieapp.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rahulgupta.imdbmovieapp.db.dao.ResultDao
import com.rahulgupta.imdbmovieapp.model.Result

@Database(entities = [Result::class], exportSchema = false, version = 1)
@TypeConverters(
    IdsToStringConverter::class
)
abstract class DatabaseService : RoomDatabase() {
    abstract fun resultDao(): ResultDao
}
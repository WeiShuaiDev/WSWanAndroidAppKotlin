package com.linwei.cams.component.database.manager

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.linwei.cams.component.database.converter.DateConverter
import com.linwei.cams.component.database.dao.SearchHistoryDao
import com.linwei.cams.component.database.entity.SearchHistoryEntity

@TypeConverters(value = [DateConverter::class])
@Database(entities = [SearchHistoryEntity::class], version = 1)
abstract class SqlDataBase : RoomDatabase() {
     abstract fun searchHistoryDao(): SearchHistoryDao?
}
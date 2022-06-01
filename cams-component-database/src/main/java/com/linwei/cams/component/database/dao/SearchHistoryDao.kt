package com.linwei.cams.component.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.linwei.cams.component.database.entity.SearchHistoryEntity

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPerson(entity: SearchHistoryEntity): Long

    @Query("select * from SearchHistoryEntity")
    fun selectHis(): List<SearchHistoryEntity?>

    @Query("delete from SearchHistoryEntity where id= :id")
    fun deleteById(id: Long)

    @Query("delete from SearchHistoryEntity")
    fun deleteAll()
}
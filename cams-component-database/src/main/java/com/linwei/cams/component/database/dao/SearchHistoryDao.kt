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

    @Query("select * from search_history_table")
    fun selectHis(): List<SearchHistoryEntity>

    @Query("delete from search_history_table where id= :id")
    fun deleteById(id: Long)

    @Query("delete from search_history_table")
    fun deleteAll()
}
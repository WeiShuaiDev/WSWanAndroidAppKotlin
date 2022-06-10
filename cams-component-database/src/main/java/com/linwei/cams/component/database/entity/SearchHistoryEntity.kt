package com.linwei.cams.component.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "search_history_table")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var insertTime: Date
) {
    @Ignore
    var name: String = ""
}
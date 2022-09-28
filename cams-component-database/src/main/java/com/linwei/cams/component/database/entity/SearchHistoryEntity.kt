package com.linwei.cams.component.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "search_history_table")
class SearchHistoryEntity(@Ignore var name: String?) {

    constructor() : this(null)

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var insertTime: Date? = null

}
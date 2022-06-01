package com.linwei.cams.component.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [Index(value = arrayOf("name"), unique = true)])
class SearchHistoryEntity @Ignore constructor(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var name: String,
    var insertTime: Date
)
package com.linwei.cams.component.database
import com.linwei.cams.component.database.manager.DatabaseManager
import com.linwei.cams.component.database.manager.SqlDataBase

/**
 * 获取 SqlDataBase
 */
fun sqlDataBase(): SqlDataBase = DatabaseManager.getInstance().getSqlDataBase()

/**
 * SearchHistoryDao
 */
val searchHistoryDao = sqlDataBase().searchHistoryDao()


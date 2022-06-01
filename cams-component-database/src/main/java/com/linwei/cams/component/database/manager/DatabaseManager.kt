package com.linwei.cams.component.database.manager

import android.text.TextUtils
import androidx.room.Room
import com.linwei.cams.component.common.ktx.app

class DatabaseManager private constructor() {
    private var mSqlDataBase: SqlDataBase? = null
    private var mDbName: String? = null

    fun init(dbName: String) {
        this.mDbName = dbName
        mSqlDataBase = null
    }

    companion object {
        private var INSTANCE: DatabaseManager? = null

        @JvmStatic
        fun getInstance(): DatabaseManager {
            return INSTANCE
                ?: DatabaseManager().apply {
                    INSTANCE = this
                }
        }
    }

    /**
     * 获取 SqlDataBase
     */
    fun getSqlDataBase(): SqlDataBase {
        if (mSqlDataBase == null) {
            if (TextUtils.isEmpty(mDbName)) {
                throw NullPointerException("dbName is null")
            }
            mSqlDataBase = Room.databaseBuilder(app, SqlDataBase::class.java, mDbName!!)
                .allowMainThreadQueries()
                .enableMultiInstanceInvalidation()
                .build()
        }
        return mSqlDataBase!!
    }
}
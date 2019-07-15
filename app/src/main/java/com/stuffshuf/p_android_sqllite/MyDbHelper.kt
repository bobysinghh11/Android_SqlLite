package com.stuffshuf.p_android_sqllite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


const val DB_NAME = "tasks.db"
const val DB_VER = 1

//first step 1-> implement SQLiteOpenHeper and then inovke the SQLite constructor by selecting 2nd const.
// use SQLiteOpenHelper 2 constructoer which have (context, curscusor factory,int)
class MyDbHelper(context: Context?): SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    /// to create table inside oncreate, we create seperate class for this TableTask
    override fun onCreate(db: SQLiteDatabase?) {
        db?.apply {
            execSQL(TableTask.CMD_CREATE_TABLE)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

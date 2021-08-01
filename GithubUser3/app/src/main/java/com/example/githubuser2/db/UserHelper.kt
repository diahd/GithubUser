package com.example.githubuser2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.githubuser2.db.UserContract.UserColumns.Companion.TABLE_NAME

internal class UserHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{

        private const val DATABASE_NAME = "dbuserfav"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_DB = "CREATE TABLE $TABLE_NAME" +
                " (${UserContract.UserColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${UserContract.UserColumns.COLUMN_NAME_USERNAME} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_NAME_NAME} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_NAME_AVA_URL} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_NAME_COMPANY} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_NAME_LOCATION} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_NAME_REPOS} TEXT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_NAME_FOLLG} INT NOT NULL," +
                " ${UserContract.UserColumns.COLUMN_NAME_FOLLS} INT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_DB)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("Drop table if exists $TABLE_NAME")
        onCreate(db)
    }
}
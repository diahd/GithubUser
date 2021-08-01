package com.example.githubuser2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.githubuser2.db.UserContract.UserColumns.Companion.COLUMN_NAME_USERNAME
import com.example.githubuser2.db.UserContract.UserColumns.Companion.TABLE_NAME
import com.example.githubuser2.db.UserContract.UserColumns.Companion._ID
import kotlin.jvm.Throws

class FavUserHelper(context: Context) {

    private lateinit var database: SQLiteDatabase
    private var databaseHelper: UserHelper = UserHelper(context)

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavUserHelper? = null

        fun getInstance(context: Context): FavUserHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: FavUserHelper(context)
            }
    }

    //membuka dan menutup databse
    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }
    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID DESC",
            null)
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$COLUMN_NAME_USERNAME = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    //menyimpan data
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$COLUMN_NAME_USERNAME = ?", arrayOf(id))
    }

    //menghapus data
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$COLUMN_NAME_USERNAME = '$id'", null)
    }

}
package com.example.githubuser2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuser2.db.FavUserHelper
import com.example.githubuser2.db.UserContract.AUTHORITY
import com.example.githubuser2.db.UserContract.UserColumns.Companion.CONTENT_URI
import com.example.githubuser2.db.UserContract.UserColumns.Companion.TABLE_NAME

class ContentProvider : ContentProvider() {

    companion object{
        private const val CONTENT = 1
        private const val CONTENT_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favUserHelper: FavUserHelper

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, CONTENT)

            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/*", CONTENT_ID)
        }
    }

    override fun onCreate(): Boolean {
        favUserHelper = FavUserHelper.getInstance(context as Context)
        favUserHelper.open()
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return when (sUriMatcher.match(uri)){
            CONTENT -> favUserHelper.queryAll()
            CONTENT_ID -> favUserHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when(CONTENT){
            sUriMatcher.match(uri) -> favUserHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val updated: Int = when (CONTENT_ID) {
            sUriMatcher.match(uri) -> favUserHelper.update(uri.lastPathSegment.toString(), values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when(CONTENT_ID){
            sUriMatcher.match(uri) -> favUserHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }

}
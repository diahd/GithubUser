package com.example.githubuser2.db

import android.net.Uri
import android.provider.BaseColumns

object UserContract {

    const val AUTHORITY = "com.example.githubuser2"
    const val SCHEME = "content"

    class UserColumns: BaseColumns{

        companion object{
            const val TABLE_NAME = "fav_user"
            const val _ID = "_id"
            const val COLUMN_NAME_USERNAME = "user"
            const val COLUMN_NAME_NAME = "name"
            const val COLUMN_NAME_AVA_URL = "photo"
            const val COLUMN_NAME_COMPANY = "company"
            const val COLUMN_NAME_LOCATION = "loc"
            const val COLUMN_NAME_REPOS = "repos"
            const val COLUMN_NAME_FOLLG = "following"
            const val COLUMN_NAME_FOLLS = "followers"

            // Base content yang digunakan untuk akses content provider
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}